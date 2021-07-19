package pablo.exchange.entities.trading.responses

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

sealed class TradeResponse()

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TradeSuccessResponse (
    @JsonProperty val tradeId: String,
    @JsonProperty val status: TradeStatus?,
    val code: Int
) : TradeResponse()

enum class TradeStatus {
    COMPLETED;
    @JsonValue
    override fun toString(): String {
        return this.name.toLowerCase()
    }
}

data class TradeErrorResponse(
    @JsonProperty val errors: List<TradeError>
) : TradeResponse()

sealed class TradeError(
    @JsonProperty val type: TradeErrorType
)

enum class TradeErrorType {
    ORDER, BLOCKCHAIN;

    @JsonValue
    override fun toString(): String {
        return this.name.toLowerCase()
    }
}

data class TradeOrderError (
    @JsonProperty val tradeId: String,
    @JsonProperty val message: String

) : TradeError(
    TradeErrorType.ORDER
)

data class TradeBlockchainError (
    @JsonProperty val tradeId: String,
    @JsonProperty val message: String
) : TradeError(
    TradeErrorType.BLOCKCHAIN
)
