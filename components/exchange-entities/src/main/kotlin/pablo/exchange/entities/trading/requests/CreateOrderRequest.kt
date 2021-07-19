package pablo.exchange.entities.trading.requests

import pablo.exchange.entities.trading.entities.OrderType

data class CreateOrderRequest (
    val amountGet: String,
    val amountGive: String,
    val type: OrderType
)