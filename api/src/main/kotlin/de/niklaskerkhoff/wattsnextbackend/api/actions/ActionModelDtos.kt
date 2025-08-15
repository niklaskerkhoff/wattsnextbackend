package de.niklaskerkhoff.wattsnextbackend.api.actions

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.energy.EnergyForm
import de.niklaskerkhoff.wattsnextbackend.model.energy.Technology
import java.util.*

data class GameDto(
    val state: GameState,
    val money: Int,
    val resources: Int,
    val currentPlayerId: UUID,
    val players: List<PlayerDto>,
    val board: BoardDto,
    val phaseIndex: Int,
    val turnInPhase: Int,
    val turnsPerPhase: Int,
    val phases: List<Phase>,
    val progressCardPileSize: Int,
)

enum class GameState {
    PREPARING,
    RUNNING,
    WON,
    LOST,
    CANCELLED
}

data class Phase(
    val generation: TargetableValue,
    val distribution: TargetableValue,
    val storage: TargetableValue,
    val progressPoints: TargetableValue,
    val electricity: TargetableValue,
    val heat: TargetableValue,
)

data class TargetableValue(
    val value: Int,
    val target: Int,
)

data class BoardDto(
    val generationCards: List<ProgressCardDto>,
    val distributionCards: List<ProgressCardDto>,
    val storageCards: List<ProgressCardDto>,
    val climateCards: List<ProgressCardDto>,
    val standardEventCard: EventCard? = null,
    val catastropheEventCard: EventCard? = null,
)

data class PlayerDto(
    val id: UUID,
    val name: String,
    val progressCards: List<ProgressCardDto>
)

data class ProgressCardDto(
    val name: String,
    val image: String,
    val text: String,
    val explanation: String,
    val moneyCosts: ModifiableValue<Int>,
    val resourceCosts: ModifiableValue<Int>,
    val originalPoints: ModifiableValue<ProgressPointDto?>,
    val supply: Supply?,
    val isPlayable: Boolean?,
    val gameBeforeEffect: GameDto?
)

data class ModifiableValue<T>(
    val originalValue: T,
    val modifiedValue: T,
    val modifications: List<Modification>
)

sealed class Modification {
    data class Stack(val multiplier: Int) : Modification()
    data class Card(val name: String) : Modification()
}

data class ProgressPointDto(
    val baseProgressPoints: Int?,
    val systemProgressPoints: Int,
    val conditions: List<Supply>,
    val conditionsFulfilled: Boolean,
)

sealed class Supply {
    abstract val type: String
    abstract val fulfilled: Boolean?

    data class Energy(
        val technology: Technology,
        val form: EnergyForm,
        val size: Int,
        override val fulfilled: Boolean?
    ) : Supply() {
        override val type: String = "ENERGY"
    }

    data class Icon(
        val iconName: String,
        override val fulfilled: Boolean?
    ) : Supply() {
        override val type: String = "ICON"
    }

    object Never : Supply() {
        override val type: String = "NEVER"
        override val fulfilled: Boolean = false
    }
}
