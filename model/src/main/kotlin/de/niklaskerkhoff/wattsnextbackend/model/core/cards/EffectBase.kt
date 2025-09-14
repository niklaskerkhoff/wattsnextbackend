package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import de.niklaskerkhoff.wattsnextbackend.model.core.Result

interface EffectBase {
    fun withNextTurn(): Result<Unit>
}
