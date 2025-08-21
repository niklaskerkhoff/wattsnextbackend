package de.niklaskerkhoff.wattsnextbackend.api.actions

import de.niklaskerkhoff.wattsnextbackend.model.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.energy.EnergyForm
import de.niklaskerkhoff.wattsnextbackend.model.energy.Technology
import java.util.*
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

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
    val climateActionCards: List<ProgressCardDto>,
    val eventCards: List<EventCard>,
    val catastropheCard: EventCard? = null,
)

data class PlayerDto(
    val id: UUID,
    val name: String,
    val handCards: List<ProgressCardDto>
)

data class ProgressCardDto(
    val name: String,
    val image: String,
    val text: String,
    val explanation: String,
    val moneyCosts: ModifiableValue<Int>,
    val resourceCosts: ModifiableValue<Int>,
    val points: ModifiableValue<ProgressPointsDto>?,
    val supply: ModifiableValue<Supply>?,
    val isPlayable: Boolean?,
    val gameBeforeEffect: GameDto?,
    // TODO: Is this the right way of differentiating between technology and climate action?
    val type: String // 'technology' or 'climateAction'
)

data class ModifiableValue<T>(
    val originalValue: T,
    // TODO: Optional or not?
    val modifiedValue: T?,
    val modifications: List<Modification>
)

// Add a type field to serialization to make Stack and Card easily distinguishable in Frontend
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = Modification.Stack::class, name = "Stack"),
    JsonSubTypes.Type(value = Modification.Card::class, name = "Card")
)
sealed class Modification {
    data class Stack(val multiplier: Int) : Modification()
    data class Card(val name: String) : Modification()
}

data class ProgressPointsDto(
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
        override val type: String = "energy"
    }

    data class Icon(
        val iconName: String, // can be CarbonCapture, NuclearWasteRepository or ChemicalEnergy
        override val fulfilled: Boolean?
    ) : Supply() {
        override val type: String = "icon"
    }

    object Never : Supply() {
        override val type: String = "never"
        override val fulfilled: Boolean = false
    }
}
