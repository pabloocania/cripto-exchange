package pablo.exchange.blockchainClient.trade

data class BlockchainCancelRequest (
    val orderId: String,
    val account:String
)