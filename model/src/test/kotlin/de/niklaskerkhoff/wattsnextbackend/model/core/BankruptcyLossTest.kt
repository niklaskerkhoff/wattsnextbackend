package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.updateMoneyEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.CardEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.values.EffectType
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * A mandatory payment (event/catastrophe effect, phase upkeep) that cannot be covered loses the
 * game immediately. Voluntary spending is blocked elsewhere by the actions' `canExecute`.
 */
class BankruptcyLossTest {

    private fun eventCard(effect: CardEffect) = EventCard(
        id = UUID.randomUUID(),
        name = "Test",
        modifierCollection = ModifierCollection(),
        effect = effect,
        phaseIndex = 0,
        isCatastrophe = false,
        eventDescription = "",
        effectDescriptions = listOf(EventCard.EffectDescription("", EffectType.MoneyAndResources)),
    )

    private fun gameWith(
        money: Int,
        eventDeck: List<EventCard>,
        turnInPhase: Int = 0,
        secondEventCardTurnInPhase: Int = 1,
    ) = Game(
        id = UUID.randomUUID(),
        state = GameState.Running,
        players = listOf(Player("P", emptyList(), UUID.randomUUID())),
        money = money,
        resources = 20,
        technologyBoard = TechnologyBoard(emptyList(), emptyList(), emptyList()),
        climateCards = emptyList(),
        progressCardDeck = emptyList(),
        standardEventCardDeck = eventDeck,
        catastropheEventCardDeck = emptyList(),
        energyTargetsPerPhase = listOf(emptyMap()),
        pointTargetsPerPhase = listOf(0),
        numberOfPhases = 1,
        numberOfTurnsPerPhase = 12,
        turnInPhase = turnInPhase,
        secondEventCardTurnInPhase = secondEventCardTurnInPhase,
    )

    @Test
    fun `prepare loses the game when the drawn event cannot be paid`() {
        val game = gameWith(money = 0, eventDeck = listOf(eventCard(updateMoneyEffect(-6))))

        val result = game.prepare()

        assertEquals(GameState.Lost, result.game.state)
        assertEquals(-6, result.game.money)
        assertEquals(true, result.baseInfo?.hasGameStateChanged)
    }

    @Test
    fun `prepare keeps the game running when the payment is covered`() {
        val game = gameWith(money = 10, eventDeck = listOf(eventCard(updateMoneyEffect(-6))))

        val result = game.prepare()

        assertEquals(GameState.Running, result.game.state)
        assertEquals(4, result.game.money)
        assertEquals(false, result.baseInfo?.hasGameStateChanged)
    }

    @Test
    fun `second event card in a phase loses the game on an unpayable cost`() {
        val game = gameWith(
            money = 3,
            eventDeck = listOf(eventCard(updateMoneyEffect(-6))),
            turnInPhase = 0,
            secondEventCardTurnInPhase = 1,
        )

        val result = game.withNextTurn()

        assertEquals(GameState.Lost, result.game.state)
        assertEquals(true, result.baseInfo?.hasGameStateChanged)
    }
}
