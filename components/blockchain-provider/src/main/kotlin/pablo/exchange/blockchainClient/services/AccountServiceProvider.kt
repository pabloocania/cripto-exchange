package pablo.exchange.blockchainClient.services

import pablo.exchange.api.shared.ExchangeApiResult
import pablo.exchange.api.shared.ExchangeApiResultSuccess
import pablo.exchange.api.trading.BlockchainService
import pablo.exchange.blockchainClient.BlockchainClient
import pablo.exchange.entities.trading.ExchangeExpert
import pablo.exchange.entities.trading.entities.Account

class AccountServiceProvider(
    private val blockchainClient: BlockchainClient
) : BlockchainService {

    override suspend fun getAccounts(): ExchangeApiResult<List<Account>> {
        return ExchangeApiResultSuccess(
            data = blockchainClient.getAccounts()
        )
    }

    override suspend fun getEthBalanceOf(accountAddress: String): ExchangeApiResult<Account> {
        return ExchangeApiResultSuccess(
            data = Account(
                address = accountAddress,
                ethBalance = ExchangeExpert.ether(blockchainClient.getEthBalanceOf(accountAddress))
            )
        )
    }

    override suspend fun getTokenBalanceOf(accountAddress: String): ExchangeApiResult<Account> {
        return ExchangeApiResultSuccess(
            data = Account(
                address = accountAddress,
                tokenBalance = ExchangeExpert.token(blockchainClient.getExchangeTokenBalanceOf("tokenAddress", accountAddress))
            )
        )
    }

    override suspend fun getFullBalanceOf(accountAddress: String): ExchangeApiResult<Account> {
        return ExchangeApiResultSuccess(
            data = Account(
                address = accountAddress,
                ethBalance = ExchangeExpert.ether(blockchainClient.getEthBalanceOf(accountAddress)),
                tokenBalance = ExchangeExpert.token(blockchainClient.getExchangeTokenBalanceOf("tokenAddress", accountAddress))
            )
        )
    }
}