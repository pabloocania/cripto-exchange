package pablo.exchange.blockchainClient

import pablo.exchange.blockchainClient.trade.BlockchainTransactionResponse
import pablo.exchange.entities.trading.entities.Account
import pablo.exchange.entities.trading.entities.ExchangeEvent
import pablo.exchange.entities.trading.entities.ExchangeEventType
import pablo.exchange.entities.trading.entities.TokenInfo

interface BlockchainClient {
      suspend fun getAccounts(): List<Account>
      suspend fun getTokenInfo() : TokenInfo
      //eth balance in wallet
      suspend fun getEthBalanceOf(account: String): String
      //token balance in wallet
      suspend fun getTokenBalanceOf(account: String): String
      //eth balance in exchange
      suspend fun getExchangeEthBalanceOf(account: String): String
      //token balance in exchange
      suspend fun getExchangeTokenBalanceOf(tokenAddress: String, account: String): String

      suspend fun getFeePercent() : String
      suspend fun getFeeAccount() : String
      suspend fun getEventsByType(eventType: ExchangeEventType) : List<ExchangeEvent>
      suspend fun cancelOrder(orderId: String) : BlockchainTransactionResponse
      suspend fun fillOrder(orderId: String) : BlockchainTransactionResponse
      suspend fun createExchangeOrder(amountGive: String, amountGet: String, type: String): BlockchainTransactionResponse
}