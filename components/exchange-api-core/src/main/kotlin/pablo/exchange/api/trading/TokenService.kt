package pablo.exchange.api.trading

import pablo.exchange.api.shared.ExchangeApiResult
import pablo.exchange.entities.trading.entities.*

interface BlockchainService {
    //Tokens
    suspend fun getTokenInfo() : ExchangeApiResult<TokenInfo> = throw RuntimeException()
    //Accounts
    suspend fun getAccounts() : ExchangeApiResult<List<Account>> = throw RuntimeException()
    suspend fun getEthBalanceOf(address: String) : ExchangeApiResult<Account> = throw RuntimeException()
    suspend fun getTokenBalanceOf(address: String) : ExchangeApiResult<Account> = throw RuntimeException()
    suspend fun getFullBalanceOf(address: String) : ExchangeApiResult<Account> = throw RuntimeException()
    //Exchange
    suspend fun getFeePercent() : ExchangeApiResult<String> = throw RuntimeException()
    suspend fun getFeeAccount() : ExchangeApiResult<String> = throw RuntimeException()
    suspend fun getEventsByType(type: String): ExchangeApiResult<List<ExchangeEvent>> = throw RuntimeException()
    suspend fun getOrders(status: String, requestAccount: String?): ExchangeApiResult<List<Order>> = throw RuntimeException()
    suspend fun cancelOrder(orderId: String): ExchangeApiResult<Transaction> = throw RuntimeException()
    suspend fun fillOrder(orderId: String): ExchangeApiResult<Transaction> = throw RuntimeException()
    suspend fun createOrder(amountGive: String, amountGet: String, type: OrderType) : ExchangeApiResult<Transaction> = throw RuntimeException()
}

