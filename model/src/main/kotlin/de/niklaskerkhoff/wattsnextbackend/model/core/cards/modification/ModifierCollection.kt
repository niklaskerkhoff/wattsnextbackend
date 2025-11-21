package de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification

import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply

/**
 *
 * effects:
 * money
 * resources
 * progressPoints
 * phaseTarget
 */

typealias SimpleModifierFunction<T> = ModifierFunction<T, ModificationBase>

typealias WithIntModifierFunction<T> = ModifierFunction<T, Pair<ModificationBase, Int>>

/**
 * A modifier can potentially modify multiple aspects of the system.
 * This class bundles all of them.
 */
class ModifierCollection(
//    val cardMoneyCostModifier: Modifier<SimpleModifierExecutorFunction<Int>>?,
//    val cardResourceCostModifier: Modifier<SimpleModifierExecutorFunction<Int>>?,

    /**
     *
     * Energiegröße für Phasenziel der aktuellen Phase
     * RequirementsForSystemPoints können nicht erfüllt werden / werden immer erfüllt
     * Supply
     * cardMoneyCost
     * cardResourceCost
     *
     *
     */

    /* val cardMoneyCostsModifierConfig: ModifierConfig<ModifierConfigurer<Int>>? = null,
     val cardResourceCostsModifierConfig: ModifierConfig<ModifierConfigurer<Int>>? = null,
     val supplyModifierConfig: ModifierConfig<ModifierConfigurer<List<Supply>>>? = null,
     val supplyRequirementsForSystemModifierConfig: ModifierConfig<ModifierConfigurer<List<Supply>>>? = null,*/

    val cardMoneyCostsModifierConfig: ModifierConfig<WithIntModifierFunction<Int>>? = null,
    val cardResourceCostsModifierConfig: ModifierConfig<WithIntModifierFunction<Int>>? = null,
    val supplyModifierConfig: ModifierConfig<WithIntModifierFunction<Supply?>>? = null,
    val supplyRequirementsForSystemModifierConfig: ModifierConfig<SimpleModifierFunction<List<Supply>>>? = null,


    /*val cardMoneyCostsModifierConfig: ModifierConfig<WithTargetPositionModifierFunction<Int>>? = null,
    val cardResourceCostsModifierConfig: ModifierConfig<WithTargetPositionModifierFunction<Int>>? = null,
    val supplyModifierConfig: ModifierConfig<SimpleModifierFunction<List<Supply>>>? = null,
    val supplyRequirementsForSystemModifierConfig: ModifierConfig<SimpleModifierFunction<List<Supply>>>? = null,*/
)

/**
 *
 * effects:
 * money
 * resources
 * progressPoints
 * phaseTarget
 */
