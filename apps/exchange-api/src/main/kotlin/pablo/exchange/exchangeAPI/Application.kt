package pablo.exchange.exchangeAPI

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.EnvironmentVariablesPropertySource
import com.sksamuel.hoplite.PropertySource
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import pablo.exchange.api.exchangeRoutes
import pablo.exchange.api.shared.ExchangeApiResultError
import pablo.exchange.api.trading.accountRoutes
import pablo.exchange.api.trading.tokenRoutes
import pablo.exchange.blockchainClient.Web3Client
import pablo.exchange.blockchainClient.Web3jClientFactory
import pablo.exchange.blockchainClient.services.AccountServiceProvider
import pablo.exchange.blockchainClient.services.ExchangeServiceProvider
import pablo.exchange.blockchainClient.services.TokenService

private val logger = LoggerFactory.getLogger("Application.main")

@KtorExperimentalAPI
@ExperimentalStdlibApi
fun Application.main() {
    val configuration = ConfigLoader.Builder()
        .addPropertySource(
            EnvironmentVariablesPropertySource(
                useUnderscoresAsSeparator = true,
                allowUppercaseNames = true
            )
        )
        .addSource(PropertySource.resource("/config.properties", true))
        .build()
        .loadConfigOrThrow<Config>()

    val tokenAddress = configuration.tokenAddress
    val exchangeAddress = configuration.exchangeAddress
    val blockchainClient = Web3Client(
        Web3jClientFactory.create(configuration.ethClientUrl),
        tokenAddress,
        exchangeAddress
    )
    val tokenService = TokenService(blockchainClient)
    val accountService = AccountServiceProvider(blockchainClient)
    val exchangeService = ExchangeServiceProvider(blockchainClient)


    install(StatusPages) {
        exception<Throwable> { e ->
            logger.warn("Received uncaught error", e)
            call.respond("Internal server error")
        }
    }
    install(DefaultHeaders)
    install(CallLogging) { level = Level.INFO }
    install(ContentNegotiation) {
        jackson {
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(KotlinModule())
            registerModule(JavaTimeModule())
        }
    }

    routing {
        healthCheck()
        route("/api") {
            tokenRoutes(tokenService)
            accountRoutes(accountService)
            exchangeRoutes(exchangeService)
        }
    }
}
