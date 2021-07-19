package pablo.exchange.blockchainClient

import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.FastRawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.gas.StaticGasProvider
import pablo.contracts.Exchange
import pablo.contracts.Token
import java.math.BigInteger

object Web3jClientFactory {

    fun create(url: String): Web3j {
        return Web3j.build(HttpService(url)) ?: throw RuntimeException("couldn't create client")
    }

    fun loadToken(tokenAddress: String, web3j: Web3j): Token{
        val txMng : TransactionManager = FastRawTransactionManager(web3j, Credentials.create(Keys.createEcKeyPair()))
        return Token.load(tokenAddress, web3j, txMng, DefaultGasProvider())
    }

    fun loadExchange(exchangeAddress: String, web3j: Web3j): Exchange {

        val GAS_LIMIT = BigInteger.valueOf(6721975)
        val GAS_PRICE = BigInteger.valueOf(20000000000)
        val txMng : TransactionManager = ClientTransactionManager(web3j, "0xeD54fB5A084BA1574920794A02E4A5f2f8AA04ED")
        return Exchange.load(exchangeAddress, web3j, txMng, GasProvider(GAS_PRICE, GAS_LIMIT))
    }
    
}

class GasProvider(
    gasPrice: BigInteger,
    gasLimit: BigInteger
) : StaticGasProvider(
    gasPrice,
    gasLimit
)