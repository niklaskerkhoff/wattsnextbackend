package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.config.technologyCards
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Position-dependent cost modifiers (e.g. "Eisenkraftwerk" is cheaper when built onto a coal plant)
 * must tolerate the no-position case. Hand cards are rendered without a target position
 * (`ProgressCardData` passes -1), so evaluating their modified cost must not touch the board and
 * must not throw — otherwise serializing that player's hand fails and the whole game state can no
 * longer be broadcast.
 */
class HandCardCostModifierTest {

    private val eisenkraftwerk =
        technologyCards.first { it.name == "Eisenkraftwerk" }

    private val kohlekraftwerk =
        technologyCards.first { it.name == "Kohlekraftwerk" }

    private fun gameWith(board: TechnologyBoard) = Game(
        id = UUID.randomUUID(),
        state = GameState.Running,
        players = listOf(Player("P", listOf(eisenkraftwerk), UUID.randomUUID())),
        money = 20,
        resources = 20,
        technologyBoard = board,
        climateCards = emptyList(),
        progressCardDeck = emptyList(),
        standardEventCardDeck = emptyList(),
        catastropheEventCardDeck = emptyList(),
        energyTargetsPerPhase = listOf(emptyMap()),
        pointTargetsPerPhase = listOf(0),
        numberOfPhases = 1,
        numberOfTurnsPerPhase = 12,
        turnInPhase = 0,
        secondEventCardTurnInPhase = 1,
    )

    private val emptyBoard = TechnologyBoard(emptyList(), emptyList(), emptyList())

    private val coalOnGeneration = TechnologyBoard(
        generationCards = listOf(listOf(kohlekraftwerk)),
        distributionCards = listOf(emptyList()),
        storageCards = listOf(emptyList()),
    )

    @Test
    fun `modified cost of a hand card without a target position falls back to the base cost`() {
        val game = gameWith(emptyBoard)

        // -1 is the value ProgressCardData uses for hand cards (no placement position yet).
        assertEquals(eisenkraftwerk.moneyCosts.base, game.getModifiedMoneyCost(eisenkraftwerk, -1))
        assertEquals(eisenkraftwerk.resourceCosts.base, game.getModifiedResourceCost(eisenkraftwerk, -1))
    }

    @Test
    fun `building iron on coal at its position still applies the discount`() {
        val game = gameWith(coalOnGeneration)
        val coalPosition = 0

        assertEquals(Technology.Generation, kohlekraftwerk.technology)
        assertEquals(4, game.getModifiedMoneyCost(eisenkraftwerk, coalPosition))
        assertEquals(1, game.getModifiedResourceCost(eisenkraftwerk, coalPosition))
    }
}
