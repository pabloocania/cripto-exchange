package pablo.exchange.blockchainClient.services

import pablo.exchange.api.shared.ExchangeApiResult
import pablo.exchange.api.shared.ExchangeApiResultSuccess
import pablo.exchange.api.trading.BlockchainService
import pablo.exchange.blockchainClient.BlockchainClient
import pablo.exchange.entities.trading.entities.TokenInfo

class TokenService(val blockchainClient: BlockchainClient) : BlockchainService {

    override suspend fun getTokenInfo(): ExchangeApiResult<TokenInfo> {
        return ExchangeApiResultSuccess(
            blockchainClient.getTokenInfo()
        )
    }

}