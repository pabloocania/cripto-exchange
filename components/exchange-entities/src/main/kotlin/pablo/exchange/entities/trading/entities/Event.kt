package pablo.exchange.entities.trading.entities

import com.fasterxml.jackson.annotation.JsonProperty

/*
    event Order(uint256 id, address user, address tokenGet, uint256 amountGet, address tokenGive, uint256 amountGive, uint256 timestamp);
    event Cancel(uint256 id, address user, address tokenGet, uint256 amountGet, address tokenGive, uint256 amountGive, uint256 timestamp);
    event Trade(uint256 id, address user, address tokenGet, uint256 amountGet, address tokenGive, uint256 amountGive, address userFill, uint256 timestamp);
 */
sealed class ExchangeEvent(
    @JsonProperty val type: ExchangeEventType,
    @JsonProperty open val id: String,
    @JsonProperty open val userAddress: String,
    @JsonProperty open val tokenGetAddress: String,
    @JsonProperty open val amountGet: String,
    @JsonProperty open val tokenGiveAddress: String,
    @JsonProperty open val amountGive: String,
    @JsonProperty open val timestamp: String
)

data class CancelExchangeEvent(
    @JsonProperty override val id: String,
    @JsonProperty override val userAddress: String,
    @JsonProperty override val tokenGetAddress: String,
    @JsonProperty override val amountGet: String,
    @JsonProperty override val tokenGiveAddress: String,
    @JsonProperty override val amountGive: String,
    @JsonProperty override val timestamp: String
) : ExchangeEvent(
    type = ExchangeEventType.CANCEL,
    id = id,
    userAddress = userAddress,
    tokenGetAddress = tokenGetAddress,
    amountGet = amountGet,
    tokenGiveAddress = tokenGiveAddress,
    amountGive = amountGive,
    timestamp = timestamp
)

data class OrderExchangeEvent(
    @JsonProperty override val id: String,
    @JsonProperty override val userAddress: String,
    @JsonProperty override val tokenGetAddress: String,
    @JsonProperty override val amountGet: String,
    @JsonProperty override val tokenGiveAddress: String,
    @JsonProperty override val amountGive: String,
    @JsonProperty override val timestamp: String
) : ExchangeEvent(
    ExchangeEventType.ORDER,
    id = id,
    userAddress = userAddress,
    tokenGetAddress = tokenGetAddress,
    amountGet = amountGet,
    tokenGiveAddress = tokenGiveAddress,
    amountGive = amountGive,
    timestamp = timestamp
)

data class TradeExchangeEvent(
    @JsonProperty override val id: String,
    @JsonProperty override val userAddress: String,
    @JsonProperty override val tokenGetAddress: String,
    @JsonProperty override val amountGet: String,
    @JsonProperty override val tokenGiveAddress: String,
    @JsonProperty override val amountGive: String,
    @JsonProperty override val timestamp: String,
    @JsonProperty val userFillAddress: String
) : ExchangeEvent(
    ExchangeEventType.TRADE,
    id = id,
    userAddress = userAddress,
    tokenGetAddress = tokenGetAddress,
    amountGet = amountGet,
    tokenGiveAddress = tokenGiveAddress,
    amountGive = amountGive,
    timestamp = timestamp
)

enum class ExchangeEventType {
    CANCEL, ORDER, TRADE
}
