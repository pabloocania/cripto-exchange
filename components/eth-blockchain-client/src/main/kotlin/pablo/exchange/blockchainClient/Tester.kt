package pablo.exchange.blockchainClient

import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

fun main(args:Array<String>){
    val web3j : Web3j
    val credentials: Credentials
    try {
        web3j = Web3j.build(HttpService("http://127.0.0.1:7545"));
        credentials = Credentials.create("67c33702b4a15ef5f9274357e769a717968f8849fee4c65d2890aa62b954e601") // directly fill in the private key of the first account
        println(web3j.web3ClientVersion().send())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}