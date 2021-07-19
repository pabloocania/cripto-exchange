package pablo.exchange.entities.trading.entities

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class Order(
    @JsonProperty val id: String,
    @JsonProperty val account: String,
    @JsonProperty val fillAccount: String? = null,
    @JsonProperty val etherAmount: Ether,
    @JsonProperty val tokenAmount: Token,
    @JsonProperty val datetime: String,
    @JsonProperty val status: OrderStatus,
    @JsonProperty val type: OrderType?
) {
    @JsonProperty val tokenPrice: BigDecimal = (etherAmount.ether.toDouble() / tokenAmount.token.toDouble()).toBigDecimal()
}

enum class OrderStatus {
    FILLED, UNFILLED, CANCELLED
}

enum class OrderType {
    BUY, SELL
}