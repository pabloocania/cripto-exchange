package pablo.exchange.entities.trading.requests

import com.fasterxml.jackson.annotation.JsonProperty
import pablo.exchange.entities.trading.entities.Trade

data class TradeRequest (
    @JsonProperty val trade: Trade
)
