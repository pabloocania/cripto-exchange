package pablo.exchange.entities.trading.entities

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigInteger

data class TokenInfo(
    @JsonProperty val totalSupply: BigInteger,
    @JsonProperty val symbol: String,
    @JsonProperty val name: String,
    @JsonProperty val decimals: String
)