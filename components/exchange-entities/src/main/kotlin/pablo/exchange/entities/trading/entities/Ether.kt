package pablo.exchange.entities.trading.entities

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class Ether(@JsonProperty val ether: BigDecimal)