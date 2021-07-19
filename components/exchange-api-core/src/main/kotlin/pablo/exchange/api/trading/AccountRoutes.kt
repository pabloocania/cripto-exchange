package pablo.exchange.api.trading

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pablo.exchange.api.shared.ExchangeApiResultSuccess

fun Route.accountRoutes (blockchainService: BlockchainService) {
    get("/accounts") {
        call.respond((blockchainService.getAccounts() as ExchangeApiResultSuccess).data)
    }

    get("/accounts/{address}"){
        val address = call.parameters["address"].toString()
        call.respond((blockchainService.getFullBalanceOf(address) as ExchangeApiResultSuccess).data)
    }

    get("accounts/{coin}/{address}"){
        val address = call.parameters["address"].toString()
        when (call.parameters["coin"].toString()) {
            "token" -> call.respond((blockchainService.getTokenBalanceOf(address) as ExchangeApiResultSuccess).data)
            "eth" -> call.respond((blockchainService.getEthBalanceOf(address) as ExchangeApiResultSuccess).data)
            else -> throw RuntimeException("Unsupported coin")
        }
    }
}