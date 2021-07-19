package pablo.exchange.entities.trading.entities

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

data class Trade (
    @JsonProperty val fromCoin: String,
    @JsonProperty val toCoin: String,
    @JsonProperty val amount: Double
)