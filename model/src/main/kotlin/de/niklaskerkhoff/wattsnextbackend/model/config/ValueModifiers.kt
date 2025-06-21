package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.modifiers.SimpleModifierProvider
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.WithTargetPositionModifierFunction
import kotlin.math.ceil

val costModifiers = mapOf<String, WithTargetPositionModifierFunction<Int>>(
    "HALF_PRICE_WHEN_X_FOLLOWING" to { modifyingCard, modifiedCard, acc, game, targetPosition ->
        game.commonAssets.getCurrentProgressCard(modifiedCard.progressCardType, targetPosition)
            .let { currentProgressCard ->
                if (currentProgressCard == modifyingCard) ceil(acc / 2.0).toInt()
                else acc
            }
    }
)


val pointModifiers = mapOf<String, SimpleModifierProvider<Int>>()
