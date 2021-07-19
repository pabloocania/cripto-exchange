package pablo.exchange.entities.trading.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class Account(
    @JsonProperty val address: String,
    @JsonProperty val ethBalance: Ether? = null,
    @JsonProperty val tokenBalance: Token? = null
)