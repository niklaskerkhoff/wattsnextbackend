package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.Tag.*
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard.TechnologyCard
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Supply
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Technology
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.SimpleModifierFunction
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.WithTargetPositionModifierFunction
import kotlin.math.max

enum class CardCostModifier(
    val modify: WithTargetPositionModifierFunction<Int>
) {
    MoneyCostsBuildingIronOnCoal({ modifyingCard, modifiedCard, acc, game, targetPosition ->
        if (isBuildingIronOnCoal(modifiedCard, game, targetPosition)) 4 else acc
    }),

    ResourceCostsBuildingIronOnCoal({ modifyingCard, modifiedCard, acc, game, targetPosition ->
        if (isBuildingIronOnCoal(modifiedCard, game, targetPosition)) 1 else acc
    }),

    CostsWithSubventionOfWindAndPhotovoltaic({ modifyingCard, modifiedCard, acc, game, targetPosition ->
        if (matches(modifiedCard) { tags.containsAny(Wind, Photovoltaic) }) max(acc - 2, 1) else acc
    }),

    CostsWithBatteryImproved({ modifyingCard, modifiedCard, acc, game, targetPosition ->
        if (matches(modifiedCard) { tags.contains(Battery) }) max(acc - 2, 1) else acc
    });

    companion object {
        private fun isBuildingIronOnCoal(builtCard: ProgressCard, game: Game, targetPosition: Int): Boolean =
            builtCard is TechnologyCard &&
                    builtCard.tags.contains(Iron) &&
                    game.technologyBoard.getCurrentTechnologyCard(builtCard.technology, targetPosition)
                        .let { currentCard ->
                            currentCard != null && currentCard.tags.contains(Coal)
                        }
    }
}

enum class SupplyModifier(
    val modify: SimpleModifierFunction<List<Supply>>
) {
    NoSupplyForOverheadPowerLine({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { tags.contains(OverheadPowerLine) }) emptyList() else acc
    }),
}

enum class SupplyListModifier(
    val modify: SimpleModifierFunction<List<Supply>>
) {
    BasePointsForSolar({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { tags.contains(Solar) }) acc + Supply.Never else acc
    }),

    BasePointsForWind({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { tags.contains(Wind) }) acc + Supply.Never else acc
    }),

    BasePointsForLargeGeneration({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { technology == Technology.Generation && supply.size >= 3 }) {
            acc + Supply.Never
        } else {
            acc
        }
    }),

    BasePointsForCoalAndGasAndNuclear({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { tags.containsAny(Coal, Gas, Nuclear) }) acc + Supply.Never else acc
    }),

    BasePointsForWaterAndPumpStorage({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { tags.containsAny(Water, PumpStorage) }) acc + Supply.Never else acc
    }),

    BasePointsForDistribution({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { technology == Technology.Distribution }) acc + Supply.Never else acc
    }),

    SystemPointsForSolar({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { tags.contains(Solar) }) emptyList() else acc
    }),

    SystemPointsForStorage({ modifyingCard: Card, modifiedCard: ProgressCard, acc: List<Supply>, game: Game ->
        if (matches(modifiedCard) { technology == Technology.Storage }) emptyList() else acc
    }),
}


private fun List<String>.contains(tag: Tag) = this.contains(tag.name)

private fun List<String>.containsAny(vararg tags: Tag) = tags.any { this.contains(it.name) }

private fun matches(card: ProgressCard, condition: TechnologyCard.() -> Boolean): Boolean =
    card is TechnologyCard && condition(card)

