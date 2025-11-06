package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import de.niklaskerkhoff.wattsnextbackend.app.actions.EntityResolver
import de.niklaskerkhoff.wattsnextbackend.app.gameinit.GameInit.Mode.StartWithCoal
import de.niklaskerkhoff.wattsnextbackend.app.gameinit.GameInit.Mode.StartWithNuclear
import de.niklaskerkhoff.wattsnextbackend.model.config.climateCards
import de.niklaskerkhoff.wattsnextbackend.model.config.eventCards
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag
import de.niklaskerkhoff.wattsnextbackend.model.config.startDistributionCards
import de.niklaskerkhoff.wattsnextbackend.model.config.startGenerationCardsWithCoal
import de.niklaskerkhoff.wattsnextbackend.model.config.startGenerationCardsWithNuclear
import de.niklaskerkhoff.wattsnextbackend.model.config.technologyCards
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import de.niklaskerkhoff.wattsnextbackend.model.core.Player
import de.niklaskerkhoff.wattsnextbackend.model.core.TechnologyBoard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology

object GameFactory {
    fun buildGame(gameInit: GameInit): Pair<Game, EntityResolver> {

        val startGenerationCards =
            when (gameInit.mode) {
                StartWithCoal -> startGenerationCardsWithCoal
                StartWithNuclear -> startGenerationCardsWithNuclear
            }

        val progressCardDeck = (technologyCards + climateCards).shuffled()
        val (players, progressCardDeckWithoutStartCards) = buildPlayers(gameInit, progressCardDeck)

        val (standardEventCardDeck, catastropheEventCardDeck) = eventCards.shuffled().partition { !it.isCatastrophe }

        val game = Game(
            id = gameInit.id,
            state = GameState.RUNNING,
            money = 10,
            resources = 20,
            technologyBoard = TechnologyBoard(
                generationCards = startGenerationCards,
                distributionCards = startDistributionCards,
                storageCards = List(3) { emptyList() },
            ),
            players = players,
            climateCards = emptyList(),
            progressCardDeck = progressCardDeckWithoutStartCards,
            standardEventCardDeck = standardEventCardDeck,
            catastropheEventCardDeck = catastropheEventCardDeck,
            pointTargetsPerPhase = listOf(30, 60, 100),
            numberOfPhases = 3,
            numberOfTurnsPerPhase = 12,
            progressPointsDelta = 0,
            energyTargetsPerPhase = listOf(
                mapOf(
                    Technology.Generation to 3,
                    Technology.Distribution to 3,
                    Technology.Storage to 0
                ),
                mapOf(
                    Technology.Generation to 6,
                    Technology.Distribution to 6,
                    Technology.Storage to 3
                ),
                mapOf(
                    Technology.Generation to 9,
                    Technology.Distribution to 9,
                    Technology.Storage to 6
                )
            ),
        )

        return Pair(game, EntityResolver(players))
    }

    private fun buildPlayers(
        gameInit: GameInit,
        progressCardDeck: List<ProgressCard>
    ): Pair<List<Player>, List<ProgressCard>> {
        val playerCount = gameInit.players.size
        // TODO: There is also a number of players with 2 cards per player
        val cardsPerPlayer = when (playerCount) {
            1 -> 6
            2 -> 5
            3 -> 4
            else -> 3
        }
        val totalPlayerCardCount = cardsPerPlayer * playerCount

        val playerCards = progressCardDeck.take(totalPlayerCardCount)
        val progressCardDeckWithoutPlayerCards = progressCardDeck.drop(totalPlayerCardCount)

        val players = gameInit.players.mapIndexed { index, playerInit ->
            Player(
                id = playerInit.id,
                name = playerInit.name,
                progressCards = playerCards.slice(index * cardsPerPlayer until (index + 1) * cardsPerPlayer)
            )
        }

        return Pair(players, progressCardDeckWithoutPlayerCards)
    }
}
