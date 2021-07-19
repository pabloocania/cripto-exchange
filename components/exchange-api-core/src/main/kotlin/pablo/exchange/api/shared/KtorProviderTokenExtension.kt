package pablo.exchange.api.shared

import io.ktor.application.*
import io.ktor.request.*
import java.security.InvalidParameterException

fun ApplicationRequest.providerTokenOrFail() =
    call.request.headers["Provider-User-Token"] ?: throw InvalidParameterException("Provider-User-Token is not specified")

