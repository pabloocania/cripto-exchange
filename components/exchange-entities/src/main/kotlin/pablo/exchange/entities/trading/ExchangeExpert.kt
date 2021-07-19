package pablo.exchange.entities.trading

import pablo.exchange.entities.trading.entities.*
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.math.pow

object ExchangeExpert {
    private val dateTimeFormatter = DateTimeFormatter.ISO_INSTANT
    const val ETHER_ADDRESS: String = "0x0000000000000000000000000000000000000000"
    private val DECIMALS: Double = 10.0.pow(18)

    fun ether(wei: String): Ether {
        return Ether(
            (wei.toDouble() / DECIMALS).toBigDecimal()
        )
    }

    fun token(value: String): Token {
        return Token(
            (value.toDouble() / DECIMALS).toBigDecimal()
        )
    }

    /**
     * @param requestAccount makes reference as a user requesting their own account id
     */
    fun makeOrderFromEvent(event: ExchangeEvent, requestAccount: String?): Order {
        return makeOrder(
            event.id,
            event.userAddress,
            event.tokenGiveAddress,
            event.amountGive,
            event.amountGet,
            event.timestamp,
            when (event.type) {
                ExchangeEventType.TRADE -> OrderStatus.FILLED
                ExchangeEventType.CANCEL -> OrderStatus.CANCELLED
                else -> OrderStatus.UNFILLED
            },
            getFillAccount(event),
            requestAccount
        )
    }

    private fun makeOrder(
        orderId: String,
        account: String,
        tokenGive: String,
        amountGive: String,
        amountGet: String,
        timestamp: String,
        status: OrderStatus,
        fillAccount: String?,
        requestAccount: String?
    ): Order {
            val (oEtherAmount, oTokenAmount) = if (tokenGive == ETHER_ADDRESS) {
                Pair(ether(amountGive), token(amountGet))
            } else {
                Pair(ether(amountGet), token(amountGive))
            }

            val isUserOrder = requestAccount == account

            val oType = if(requestAccount != null && isUserOrder){
                if(tokenGive == ETHER_ADDRESS) OrderType.BUY else OrderType.SELL
            } else if(requestAccount != null && !isUserOrder){
                if(tokenGive == ETHER_ADDRESS) OrderType.SELL else OrderType.BUY
            } else {
                if(tokenGive == ETHER_ADDRESS) OrderType.BUY else OrderType.SELL
            }

            return Order(
                id = orderId,
                account = account,
                etherAmount = oEtherAmount,
                tokenAmount = oTokenAmount,
                datetime = dateTimeFormatter.format(Instant.ofEpochMilli(timestamp.toLong() * 1000)),
                status = status,
                type = oType,
                fillAccount = fillAccount
            )
    }

    private fun getFillAccount(event: ExchangeEvent) : String? {
        return if(event is TradeExchangeEvent) return event.userFillAddress else null
    }
}