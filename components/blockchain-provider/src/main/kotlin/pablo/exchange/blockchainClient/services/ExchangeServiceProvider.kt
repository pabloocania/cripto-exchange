package pablo.exchange.blockchainClient.services

import pablo.exchange.api.shared.ExchangeApiResult
import pablo.exchange.api.shared.ExchangeApiResultSuccess
import pablo.exchange.api.trading.BlockchainService
import pablo.exchange.blockchainClient.BlockchainClient
import pablo.exchange.blockchainClient.trade.BlockchainTransactionResponse
import pablo.exchange.entities.trading.ExchangeExpert
import pablo.exchange.entities.trading.entities.*
import java.lang.RuntimeException

class ExchangeServiceProvider(private val blockchainClient: BlockchainClient) : BlockchainService {

    override suspend fun getFeePercent(): ExchangeApiResult<String> {
        return ExchangeApiResultSuccess(
            data = blockchainClient.getFeePercent()
        )
    }

    override suspend fun getFeeAccount(): ExchangeApiResult<String> {
        return ExchangeApiResultSuccess(
            data = blockchainClient.getFeeAccount()
        )
    }

    override suspend fun getEventsByType(type: String): ExchangeApiResult<List<ExchangeEvent>>{
        return ExchangeApiResultSuccess(
            data = blockchainClient.getEventsByType(
                when(type.toLowerCase()) {
                    "cancel" -> ExchangeEventType.CANCEL
                    "order" -> ExchangeEventType.ORDER
                    "trade" -> ExchangeEventType.TRADE
                    else -> throw RuntimeException("Invalid type event")
                }
            )
        )
    }

    override suspend fun getOrders(status: String, requestAccount: String?): ExchangeApiResult<List<Order>> {
        val orderStatus = mapOrderStatus(status)

        return when(orderStatus){
            OrderStatus.FILLED -> {
                blockchainClient.getEventsByType(ExchangeEventType.TRADE)
            }
            OrderStatus.CANCELLED -> {
                blockchainClient.getEventsByType(ExchangeEventType.CANCEL)
            }
            OrderStatus.UNFILLED -> {
                blockchainClient.getEventsByType(ExchangeEventType.ORDER)
                    .filterNot { exchangeEvent -> blockchainClient.getEventsByType(ExchangeEventType.CANCEL).any { it.id == exchangeEvent.id } }
                    .filterNot { exchangeEvent -> blockchainClient.getEventsByType(ExchangeEventType.TRADE).any { it.id == exchangeEvent.id } }
            }
            else -> emptyList()
        }.filter {
            filterAccountsFromUser(requestAccount, orderStatus, it)
        }
        .map {
            ExchangeExpert.makeOrderFromEvent(it, requestAccount)
        }
        .let {
            ExchangeApiResultSuccess(data = it)
        }
    }

    override suspend fun cancelOrder(orderId: String): ExchangeApiResult<Transaction> {
        return ExchangeApiResultSuccess(data = Transaction(blockchainClient.cancelOrder(orderId).hash))
    }

    override suspend fun fillOrder(orderId: String): ExchangeApiResult<Transaction>{
        return ExchangeApiResultSuccess(data = Transaction(blockchainClient.fillOrder(orderId).hash))
    }

    override suspend fun createOrder(amountGive: String, amountGet: String, type: OrderType) : ExchangeApiResult<Transaction> {
        return ExchangeApiResultSuccess(
            data = Transaction(blockchainClient.createExchangeOrder(amountGive, amountGet, type.name).hash)
        )
    }

    private fun mapOrderStatus(status: String): OrderStatus {
        return when (status.toUpperCase()) {
            "FILLED" -> OrderStatus.FILLED
            "CANCELLED" -> OrderStatus.CANCELLED
            "UNFILLED" -> OrderStatus.UNFILLED
            else -> throw RuntimeException("Invalid status")
        }
    }

    private fun filterAccountsFromUser(
        requestAccount: String?,
        status: OrderStatus,
        it: ExchangeEvent
    ): Boolean {
        return if (requestAccount != null) {
            if (OrderStatus.FILLED == status) {
                it.userAddress == requestAccount || (it as TradeExchangeEvent).userFillAddress == requestAccount
            } else {
                it.userAddress == requestAccount
            }
        } else
            true
    }
}