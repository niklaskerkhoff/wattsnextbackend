package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology

data class PhaseData(
    val generation: TargetableValue,
    val distribution: TargetableValue,
    val storage: TargetableValue,
    val progressPoints: TargetableValue,
    val electricity: TargetableValue,
    val heat: TargetableValue,
    val money: TargetableValue,
) {

    constructor(result: Result<*>, phaseIndex: Int) : this(
        generation = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { it.generation },
            liveValue = { result.game.getGeneration() },
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]
                ?: throw IllegalStateException("No generation target found.")
        ),
        distribution = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { it.distribution },
            liveValue = { result.game.getDistribution() },
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Distribution]
                ?: throw IllegalStateException("No distribution target found.")
        ),
        storage = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { it.storage },
            liveValue = { result.game.getStorage() },
            target = result.game.energyTargetsPerPhase[phaseIndex][Technology.Storage]
                ?: throw IllegalStateException("No storage target found.")
        ),
        progressPoints = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { it.progressPoints },
            liveValue = { result.game.calculateProgressPointInfo().progressPoints },
            target = result.game.pointTargetsPerPhase[phaseIndex]
        ),
        electricity = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { if (it.electricity) 1 else 0 },
            liveValue = { if (result.game.doesElectricityExist()) 1 else 0 },
            target = 1
        ),
        heat = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { if (it.heat) 1 else 0 },
            liveValue = { if (result.game.doesHeatExist()) 1 else 0 },
            target = 1
        ),
        money = targetable(
            result = result,
            phaseIndex = phaseIndex,
            snapshotValue = { it.moneyEarned },
            liveValue = { result.game.getMoneyEarned(phaseIndex) },
            target =
                minOf(
                    result.game.energyTargetsPerPhase[phaseIndex][Technology.Generation]!!,
                    result.game.energyTargetsPerPhase[phaseIndex][Technology.Distribution]!!
                ) +
                        result.game.energyTargetsPerPhase[phaseIndex][Technology.Storage]!!
        )
    )

    companion object {

        private fun targetable(
            result: Result<*>,
            phaseIndex: Int,
            snapshotValue: (Game.PhaseSnapshot) -> Int,
            liveValue: () -> Int,
            target: Int
        ): TargetableValue {

            val snapshot = result.game.phaseSnapshots.getOrNull(phaseIndex)

            return TargetableValue(
                value = snapshot?.let(snapshotValue) ?: liveValue(),
                target = target
            )
        }
    }
}
