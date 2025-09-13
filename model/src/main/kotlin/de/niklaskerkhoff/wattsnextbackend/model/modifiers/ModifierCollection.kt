package de.niklaskerkhoff.wattsnextbackend.model.modifiers

import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Supply

/**
 * A modifier can potentially modify multiple aspects of the system.
 * This class bundles all of them.
 *
 *
 * @property costModifier Modifies the cost of an action.
 * @property pointModifier Modifies the points of an action.
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


    val cardMoneyCostsModifier: Modifier<WithTargetPositionModifierFunction<Int>>? = null,
    val cardResourceCostsModifier: Modifier<WithTargetPositionModifierFunction<Int>>? = null,
    val supplyModifier: Modifier<SimpleModifierFunction<List<Supply>>>? = null,
    val supplyRequirementsForSystemModifier: Modifier<SimpleModifierFunction<List<Supply>>>? = null,
    val costModifier: Modifier<WithTargetPositionModifierFunction<Int>>? = null,
    val pointModifier: Modifier<SimpleModifierFunction<Int>>? = null,
)

/**
 *
 * effects:
 * money
 * resources
 * progressPoints
 * phaseTarget
 */
