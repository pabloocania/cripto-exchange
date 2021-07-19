package pablo.exchange.api.shared

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped

sealed class ExchangeApiResult<T>

data class ExchangeApiResultSuccess<T>(
    @JsonUnwrapped val data: T
) : ExchangeApiResult<T>()

data class ExchangeApiResultError<T>(
    @JsonProperty val message: String?,
    @JsonProperty val statusCode: String?
) : ExchangeApiResult<T>()
