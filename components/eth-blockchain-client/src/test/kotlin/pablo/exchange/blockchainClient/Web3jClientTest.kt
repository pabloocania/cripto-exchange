package pablo.exchange.blockchainClient

import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.tx.FastRawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import pablo.contracts.Exchange
import pablo.contracts.Token
import pablo.exchange.entities.trading.ExchangeExpert.ether
import java.math.BigDecimal
import kotlin.test.assertEquals

class Web3jClientTest {

    val tokenAddress = "0x29b3D40D9799556Dd437395338eE24F7857B95a7"
    val exchangeAddress = "0xA7C22b423a97884a9d4D1764AA0f1fC9B15E9FFb"
    val client = Web3Client(Web3jClientFactory.create("http://127.0.0.1:7545"), tokenAddress, exchangeAddress)

    @Test
    fun testConnection() = runBlocking {

        val txMng : TransactionManager = FastRawTransactionManager(client.web3j, Credentials.create(Keys.createEcKeyPair()))

        val blockN = client.getBlockNumber()

        val token = Token.load(tokenAddress, client.web3j, txMng, DefaultGasProvider())
        val exchange = Exchange.load(exchangeAddress, client.web3j, txMng, DefaultGasProvider())

        assertEquals("QTOKEN", token.name().sendAsync().get())
        assertEquals("1000000000000000000000000", token.totalSupply().sendAsync().get().toString())
    }

    @Test
    fun testDepositEth() {
        runBlocking {

            val ethBalance = client.getEthBalanceOf("0xeD54fB5A084BA1574920794A02E4A5f2f8AA04ED")
            val ethExchange = client.getExchangeEthBalanceOf("0xeD54fB5A084BA1574920794A02E4A5f2f8AA04ED")
            val hash = client.depositEther(BigDecimal(1))
            val newEthExchangeBalance = client.getExchangeEthBalanceOf("0xeD54fB5A084BA1574920794A02E4A5f2f8AA04ED")
            val newEthBalance = client.getEthBalanceOf("0xeD54fB5A084BA1574920794A02E4A5f2f8AA04ED")

            assertTrue(hash.isNotEmpty())
            assertEquals(ether(ethBalance).ether - BigDecimal(1), ether(newEthBalance).ether)
            assertEquals(ether(ethExchange).ether - BigDecimal(1),ether(newEthExchangeBalance).ether)

        }
    }
}