package de.niklaskerkhoff.wattsnextbackend.model.config.helper

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffectInfo
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard

fun updateMoneyEffect(amount: Int): CardEffect = { game ->
    Pair(game.withUpdatedMoney(amount), listOf(CardEffectInfo.Money(amount)))
}

fun updateResourcesEffect(resources: Int): CardEffect = { game ->
    Pair(game.withUpdatedResources(resources), listOf(CardEffectInfo.Resources(resources)))
}

fun updateProgressPointsEffect(progressPoints: Int): CardEffect = { game ->
    Pair(
        game.withAdditionalProgressPoints(progressPoints),
        listOf(CardEffectInfo.ProgressPoints(progressPoints))
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

fun ifGasIsExisting(effect: CardEffect): CardEffect = ifTagIsExisting(Tag.Gas, effect)

fun ifSolarIsExisting(effect: CardEffect): CardEffect = ifTagIsExisting(Tag.Solar, effect)

fun ifWindIsExisting(effect: CardEffect): CardEffect = ifTagIsExisting(Tag.Wind, effect)

private fun ifTagIsExisting(tag: Tag, effect: CardEffect): CardEffect = { game ->
    val isExisting = game.getAllCards().any {
        it is ProgressCard.TechnologyCard && it.tags.contains(tag.name)
    }

    if (isExisting) effect(game) else Pair(game, emptyList())
}

infix fun CardEffect.and(other: CardEffect): CardEffect = { game ->
    val (firstGame, firstInfo) = this(game)
    val (secondGame, secondInfo) = other(firstGame)
    Pair(secondGame, firstInfo + secondInfo)
}
