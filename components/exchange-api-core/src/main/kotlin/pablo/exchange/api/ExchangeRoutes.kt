package pablo.exchange.api

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pablo.exchange.api.shared.ExchangeApiResultSuccess
import pablo.exchange.api.trading.BlockchainService
import pablo.exchange.entities.trading.entities.OrderStatus
import pablo.exchange.entities.trading.entities.OrderType
import pablo.exchange.entities.trading.requests.CreateOrderRequest
import java.lang.RuntimeException

fun Route.exchangeRoutes(blockchainService: BlockchainService) {
    get("/exchange/fee_percent") {
        call.respond((blockchainService.getFeePercent() as ExchangeApiResultSuccess).data)
    }

    get("/exchange/fee_account") {
        call.respond((blockchainService.getFeeAccount() as ExchangeApiResultSuccess).data)
    }

    get("/exchange/events/{eventType}") {
        //val eventType = call.request.queryParameters["event_type"] ?: throw RuntimeException()
        val eventType = call.parameters["eventType"] ?: throw RuntimeException("Missing path parameter eventType")
        call.respond(blockchainService.getEventsByType(eventType))
    }

    get("exchange/orders/{status}") {
        val status = call.parameters["status"] ?: throw RuntimeException("Missing status path parameter")

        val account = call.request.queryParameters["account"]
        val safeAccount =
            if (account == "") throw RuntimeException("Invalid account") else account //TODO: Validate account format
        call.respond(blockchainService.getOrders(status, safeAccount))
    }

    post("exchange/order/{orderId}/cancel") {
        val orderId =
            call.parameters["orderId"] ?: throw RuntimeException("Invalid orderId")//TODO: Validate account format
        call.respond(blockchainService.cancelOrder(orderId))
    }

    post("exchange/order/{orderId}/fill") {
        val orderId =
            call.parameters["orderId"] ?: throw RuntimeException("Invalid orderId")//TODO: Validate account format
        call.respond(blockchainService.fillOrder(orderId))
    }

    post<CreateOrderRequest>("exchange/order") { createOrderReq ->
        //values are not in Wei, but in decimals
        //TODO: validate amounts
        call.respond(
            blockchainService.createOrder(
                amountGet = createOrderReq.amountGet,
                amountGive = createOrderReq.amountGive,
                type = createOrderReq.type
            )
        )
    }
}
