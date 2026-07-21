package de.niklaskerkhoff.wattsnextbackend.model.config.helper

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag.*
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard.TechnologyCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModificationBase
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.SimpleModifierFunction
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.WithIntModifierFunction
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology
import kotlin.math.max

enum class CardCostModifier(
    val modify: WithIntModifierFunction<Int>
) {
    MoneyCostsBuildingIronOnCoal({ acc, modifiedCard, (game, targetPosition) ->
        if (isBuildingIronOnCoal(modifiedCard, game, targetPosition)) 4 else acc
    }),

    ResourceCostsBuildingIronOnCoal({ acc, modifiedCard, (game, targetPosition) ->
        if (isBuildingIronOnCoal(modifiedCard, game, targetPosition)) 1 else acc
    }),

    MoneyCostsBuildingBigWindOnSmallWind({ acc, modifiedCard, (game, targetPosition) ->
        if (isBuildingBigWindOnSmallWind(modifiedCard, game, targetPosition)) 1 else acc
    }),

    ResourceCostsBuildingBigWindOnSmallWind({ acc, modifiedCard, (game, targetPosition) ->
        if (isBuildingBigWindOnSmallWind(modifiedCard, game, targetPosition)) 1 else acc
    }),

    CostsWithSubventionOfWindAndPhotovoltaic({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.containsAny(Wind, Photovoltaic) }) max(acc - 2, 1) else acc
    }),

    CostsWithBatteryImproved({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.contains(Battery) }) max(acc - 2, 0) else acc
    });

    companion object {
        private fun isBuildingIronOnCoal(
            builtCard: ProgressCard,
            modificationBase: ModificationBase,
            targetPosition: Int
        ): Boolean =
            builtCard is TechnologyCard &&
                    builtCard.tags.contains(Iron) &&
                    modificationBase.technologyBoard.getCurrentTechnologyCard(builtCard.technology, targetPosition)
                        .let { currentCard ->
                            currentCard != null && currentCard.tags.contains(Coal)
                        }

        // "Kleiner Windpark" and "Großer Windpark" share the Wind tag, so they are told apart by
        // supply size (Kleiner = 2, Großer = 3).
        private fun isBuildingBigWindOnSmallWind(
            builtCard: ProgressCard,
            modificationBase: ModificationBase,
            targetPosition: Int
        ): Boolean =
            builtCard is TechnologyCard &&
                    builtCard.tags.contains(Wind) &&
                    builtCard.supply.base.size == 3 &&
                    modificationBase.technologyBoard.getCurrentTechnologyCard(builtCard.technology, targetPosition)
                        .let { currentCard ->
                            currentCard != null && currentCard.tags.contains(Wind) && currentCard.supply.base.size == 2
                        }
    }
}

enum class SupplyModifier(
    val modify: WithIntModifierFunction<Supply?>
) {
    NoSupplyFromOverheadPowerLine({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.contains(OverheadPowerLine) } && acc is Supply.Energy) acc.copy(size = 0) else acc
    }),

    Stack({ acc, modifiedCard, (game, targetPosition) ->
        if (targetPosition < 0) acc
        else if (modifiedCard !is TechnologyCard) acc
        else if (modifiedCard !=
            game.technologyBoard.getCurrentTechnologyCard(modifiedCard.technology, targetPosition)
        ) acc
        else {
            val stack = game.technologyBoard.getSameTechnologyCardStack(modifiedCard.technology, targetPosition)
            Supply.Energy(
                technology = modifiedCard.technology,
                form = modifiedCard.supply.base.form,
                size = stack.sumOf { it.supply.base.size }
            )
        }
    })
}

enum class SupplyListModifier(
    val modify: SimpleModifierFunction<List<Supply>>
) {
    BasePointsForSolar({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.contains(Solar) }) acc + Supply.Never else acc
    }),

    BasePointsForWind({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.contains(Wind) }) acc + Supply.Never else acc
    }),

    BasePointsForLargeGeneration({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { technology == Technology.Generation && supply.base.size >= 3 }) {
            acc + Supply.Never
        } else {
            acc
        }
    }),

    BasePointsForCoalAndGasAndNuclear({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.containsAny(Coal, Gas, Nuclear) }) acc + Supply.Never else acc
    }),

    BasePointsForWaterAndPumpStorage({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.containsAny(Water, PumpStorage) }) acc + Supply.Never else acc
    }),

    BasePointsForDistributionCondition({ acc, modifiedCard, _ ->
        if (progressCardMatches(modifiedCard)
            { supplyRequirementsForSystem.base.any { it is Supply.Energy && it.technology == Technology.Distribution } })
            acc + Supply.Never
        else acc
    }),

    SystemPointsForSolar({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { tags.contains(Solar) }) emptyList() else acc
    }),

    SystemPointsForStorage({ acc, modifiedCard, _ ->
        if (matches(modifiedCard) { technology == Technology.Storage }) emptyList() else acc
    }),
}

fun <T> ifWindIsExistingModifier(modifier: SimpleModifierFunction<T>) = ifTagIsExistingModifier(Wind, modifier)

fun <T> ifSolarIsExistingModifier(modifier: SimpleModifierFunction<T>) = ifTagIsExistingModifier(Solar, modifier)

private fun <T> ifTagIsExistingModifier(
    tag: Tag,
    modifier: SimpleModifierFunction<T>
): SimpleModifierFunction<T> =
    ifCondition(
        condition = { base ->
            base.technologyBoard.getAllCurrentProgressCards().any {
                it?.tags?.contains(tag) ?: false
            }
        },
        modifier = modifier
    )

private fun <T> ifCondition(
    condition: (ModificationBase) -> Boolean,
    modifier: SimpleModifierFunction<T>
): SimpleModifierFunction<T> =
    { acc, modifiedCard, modificationBase ->
        if (condition(modificationBase))
            modifier(acc, modifiedCard, modificationBase)
        else
            acc
    }

private fun List<Tag>.contains(tag: Tag) = this.contains(tag)

private fun List<Tag>.containsAny(vararg tags: Tag) = tags.any { this.contains(it) }

private fun matches(card: ProgressCard, condition: TechnologyCard.() -> Boolean): Boolean =
    card is TechnologyCard && condition(card)

private fun progressCardMatches(card: ProgressCard, condition: ProgressCard.()-> Boolean): Boolean = condition(card)

