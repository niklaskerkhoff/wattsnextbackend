package de.niklaskerkhoff.wattsnextbackend.model.config.helper

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInformation
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard

fun updateMoneyEffect(amount: Int): CardEffect = { game ->
    Pair(game.withUpdatedMoney(amount), listOf(CardEffectInformation.Money(amount)))
}

fun updateResourcesEffect(resources: Int): CardEffect = { game ->
    Pair(game.withUpdatedResources(resources), listOf(CardEffectInformation.Resources(resources)))
}

fun updateProgressPointsEffect(progressPoints: Int): CardEffect = { game ->
    Pair(
        game.withAdditionalProgressPoints(progressPoints),
        listOf(CardEffectInformation.ProgressPoints(progressPoints))
    )
}

fun updateCurrentPhaseTarget(target: Int): CardEffect = { game ->
    Pair(game, emptyList())//Impl
}

fun updateMoneyPerPlayerEffect(multiplier: Int): CardEffect = { game ->
    updateMoneyEffect(game.players.count() * multiplier)(game)
}

fun nuclearCatastropheIfExistingEffect(): CardEffect = { game ->
    Pair(game, emptyList())//Impl
}

fun ifSolarIsExisting(effect: CardEffect): CardEffect = { game ->
    val solarExisting = game.getAllCards().any {
        it is ProgressCard.TechnologyCard && it.tags.contains(Tag.Solar.name)
    }
    if (solarExisting) effect(game) else Pair(game, emptyList())
}

fun ifWindIsExisting(effect: CardEffect): CardEffect = { game ->
    val windExisting = game.getAllCards().any {
        it is ProgressCard.TechnologyCard && it.tags.contains(Tag.Wind.name)
    }
    if (windExisting) effect(game) else Pair(game, emptyList())
}

infix fun CardEffect.and(other: CardEffect): CardEffect = { game ->
    val (firstGame, firstInformation) = this(game)
    val (secondGame, secondInformation) = other(firstGame)
    Pair(secondGame, firstInformation + secondInformation)
}
