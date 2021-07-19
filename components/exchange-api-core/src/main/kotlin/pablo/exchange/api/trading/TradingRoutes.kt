package pablo.exchange.api.trading

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pablo.exchange.api.shared.ExchangeApiResultSuccess

fun Route.tokenRoutes(blockchainService: BlockchainService) {
    get ("/token") {
        call.respond((blockchainService.getTokenInfo() as ExchangeApiResultSuccess).data)
    }
}