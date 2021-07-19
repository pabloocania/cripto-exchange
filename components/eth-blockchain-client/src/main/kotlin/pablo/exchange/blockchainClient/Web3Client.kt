package pablo.exchange.blockchainClient

import org.web3j.abi.EventEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthBlockNumber
import org.web3j.utils.Convert
import pablo.contracts.Exchange.*
import pablo.exchange.blockchainClient.trade.BlockchainTransactionResponse
import pablo.exchange.entities.trading.ExchangeExpert
import pablo.exchange.entities.trading.entities.*
import java.math.BigDecimal
import java.math.BigInteger

//TODO: split this class into different clients for different contracts
class Web3Client(val web3j: Web3j, tokenAddress: String, exchangeAddress: String) : BlockchainClient {

    private val token = Web3jClientFactory.loadToken(tokenAddress, web3j)
    private val exchange = Web3jClientFactory.loadExchange(exchangeAddress, web3j)

    fun getBlockNumber(): EthBlockNumber? {
        return web3j.ethBlockNumber()
            .sendAsync()
            .get()
    }


    override suspend fun getAccounts(): List<Account> {
        return web3j.ethAccounts().sendAsync().get().accounts.mapNotNull {
            Account(
                it
            )
        }
    }

    override suspend fun getTokenInfo(): TokenInfo {
        return TokenInfo(
            name = token.name().sendAsync().get(),
            symbol = token.symbol().sendAsync().get(),
            totalSupply = BigInteger(token.totalSupply().sendAsync().get().toString()),
            decimals = token.decimals().sendAsync().get().toString()
        )
    }

    override suspend fun  getExchangeTokenBalanceOf(tokenAddress: String, account: String): String {
        //TODO: WHEN MORE TOKENS, WE NEED TO CHANGE THIS AND MAP THEM TO THE ADDRESSES
        return exchange.balanceOf(token.contractAddress, account).sendAsync().get().toString()
    }

    override suspend fun getExchangeEthBalanceOf(account: String) : String {
        return exchange.balanceOf(ExchangeExpert.ETHER_ADDRESS, account).sendAsync().get().toString()
    }

    override suspend fun getTokenBalanceOf(account: String) : String{
        return token.balanceOf(account).sendAsync().get().toString()
    }

    override suspend fun getEthBalanceOf(account: String): String {
        return web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).sendAsync().get().balance.toString()
    }

    override suspend fun getFeePercent(): String {
        return exchange.feePercent().sendAsync().get().toString()
    }

    override suspend fun getFeeAccount(): String {
        return exchange.feeAccount().sendAsync().get().toString()
    }

    override suspend fun getEventsByType(eventType: ExchangeEventType): List<ExchangeEvent> {
        val returnList = ArrayList<ExchangeEvent>()
        val filter =
            EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, exchange.contractAddress)

        return when (eventType) {
            ExchangeEventType.CANCEL -> {
                filter.addSingleTopic(EventEncoder.encode(CANCEL_EVENT))
                exchange.cancelEventFlowable(filter)
                    .map {
                        CancelExchangeEvent(
                            id = it.id.toString(),
                            userAddress = it.user,
                            tokenGetAddress = it.tokenGet.toString(),
                            amountGet = it.amountGet.toString(),
                            tokenGiveAddress = it.tokenGive.toString(),
                            amountGive = it.amountGive.toString(),
                            timestamp = it.timestamp.toString()
                        )
                    }
                    .subscribe { returnList.add(it) }
                    .dispose()
                returnList
            }
            ExchangeEventType.TRADE -> {
                filter.addSingleTopic(EventEncoder.encode(TRADE_EVENT))
                exchange.tradeEventFlowable(filter).map {
                    TradeExchangeEvent(
                        id = it.id.toString(),
                        userAddress = it.user,
                        tokenGetAddress = it.tokenGet.toString(),
                        amountGet = it.amountGet.toString(),
                        tokenGiveAddress = it.tokenGive.toString(),
                        amountGive = it.amountGive.toString(),
                        timestamp = it.timestamp.toString(),
                        userFillAddress = it.userFill
                    )
                }
                    .subscribe { returnList.add(it) }
                    .dispose()
                returnList
            }
            ExchangeEventType.ORDER -> {
                filter.addSingleTopic(EventEncoder.encode(ORDER_EVENT))
                exchange.orderEventFlowable(filter).map {
                    OrderExchangeEvent(
                        id = it.id.toString(),
                        userAddress = it.user,
                        tokenGetAddress = it.tokenGet.toString(),
                        amountGet = it.amountGet.toString(),
                        tokenGiveAddress = it.tokenGive.toString(),
                        amountGive = it.amountGive.toString(),
                        timestamp = it.timestamp.toString()
                    )
                }
                    .subscribe { returnList.add(it) }
                    .dispose()
                returnList
            }
            else -> emptyList()
        }
    }

    override suspend fun cancelOrder(orderId: String): BlockchainTransactionResponse {
        return BlockchainTransactionResponse(
            exchange.cancelOrder(orderId.toBigInteger()).sendAsync().get().transactionHash
        )
    }

    override suspend fun fillOrder(orderId: String): BlockchainTransactionResponse {
        return BlockchainTransactionResponse(
            exchange.fillOrder(orderId.toBigInteger()).sendAsync().get().transactionHash
        )
    }

    override suspend fun createExchangeOrder(amountGive: String, amountGet: String, type: String): BlockchainTransactionResponse {
        return BlockchainTransactionResponse(
            when (type.toUpperCase()) {
                //Exchange is giving eth and getting tokens
                "BUY" -> createOrder(
                    tokenGetAddress = token.contractAddress,
                    amountGet = toWei(amountGet),
                    tokenGiveAddress = ExchangeExpert.ETHER_ADDRESS,
                    amountGive = toWei(amountGive)
                )
                //Exchange is giving token and gets eth
                "SELL" -> createOrder(
                    tokenGetAddress = ExchangeExpert.ETHER_ADDRESS,
                    amountGet = toWei(amountGet),
                    tokenGiveAddress = token.contractAddress,
                    amountGive = toWei(amountGive)
                )
                else -> throw RuntimeException("Couldn't create Order")
            }
        )
    }

    fun createOrder(tokenGetAddress: String, amountGet: BigInteger, tokenGiveAddress: String, amountGive: BigInteger): String {
        return exchange.makeOrder(
            tokenGetAddress,
            amountGet,
            tokenGiveAddress,
            amountGive
        ).sendAsync().get().transactionHash
    }

    fun depositEther(amount: BigDecimal) : String {
        return exchange.depositEther(Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger()).sendAsync().get().transactionHash
    }

    private fun toWei(value: String) : BigInteger {
        return Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()
    }

}