package pablo.exchange.exchangeAPI

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.healthCheck(){
    get("health_check"){
        call.respond(HttpStatusCode.OK)
    }
}