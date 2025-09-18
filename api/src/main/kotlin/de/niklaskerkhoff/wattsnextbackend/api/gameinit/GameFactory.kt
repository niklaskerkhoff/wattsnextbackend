package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import de.niklaskerkhoff.wattsnextbackend.api.actions.EntityResolver
import de.niklaskerkhoff.wattsnextbackend.api.gameinit.GameInit.Mode.StartWithCoal
import de.niklaskerkhoff.wattsnextbackend.api.gameinit.GameInit.Mode.StartWithNuclear
import de.niklaskerkhoff.wattsnextbackend.model.config.climateCards
import de.niklaskerkhoff.wattsnextbackend.model.config.eventCards
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag
import de.niklaskerkhoff.wattsnextbackend.model.config.technologyCards
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import de.niklaskerkhoff.wattsnextbackend.model.core.Player
import de.niklaskerkhoff.wattsnextbackend.model.core.TechnologyBoard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.lib.removed

object GameFactory {
    fun buildGame(gameInit: GameInit): Pair<Game, EntityResolver> {

        val startTechnologyCard =
            when (gameInit.mode) {
                StartWithCoal -> technologyCards.first { it.tags.contains(Tag.Coal.name) }
                StartWithNuclear -> technologyCards.first { it.tags.contains(Tag.Nuclear.name) }
            }


        val progressCardDeck = (technologyCards + climateCards).shuffled().removed(startTechnologyCard)
        val (players, progressCardDeckWithoutStartCard) = buildPlayers(gameInit, progressCardDeck)

        val (standardEventCardDeck, catastropheEventCardDeck) = eventCards.shuffled().partition { !it.isCatastrophe }

        val game = Game(
            id = gameInit.id,
            state = GameState.RUNNING,
            money = 10,
            resources = 10,
            technologyBoard = TechnologyBoard(
                generationCards = listOf(listOf(startTechnologyCard), emptyList(), emptyList()),
                distributionCards = List(3) { emptyList() },
                storageCards = List(3) { emptyList() },
            ),
            players = players,
            climateCards = emptyList(),
            progressCardDeck = progressCardDeckWithoutStartCard,
            standardEventCardDeck = standardEventCardDeck,
            catastropheEventCardDeck = catastropheEventCardDeck,
            energyTargetsPerPhase = emptyList(),
            pointTargetsPerPhase = emptyList(),
            numberOfPhases = 0,
            numberOfTurnsPerPhase = 12,
        )

        return Pair(game, EntityResolver(players))
    }

    private fun buildPlayers(
        gameInit: GameInit,
        progressCardDeck: List<ProgressCard>
    ): Pair<List<Player>, List<ProgressCard>> {
        val playerCount = gameInit.players.size
        val cardsPerPlayer = when (playerCount) {
            2 -> 5
            3 -> 4
            else -> 3
        }
        val totalPlayerCardCount = cardsPerPlayer * playerCount

        val playerCards = progressCardDeck.take(totalPlayerCardCount)
        val progressCardDeckWithoutPlayerCards = progressCardDeck.drop(totalPlayerCardCount)

        val players = gameInit.players.mapIndexed { index, playerInit ->
            Player(
                name = playerInit.name,
                progressCards = playerCards.slice(index * cardsPerPlayer until (index + 1) * cardsPerPlayer)
            )
        }

        return Pair(players, progressCardDeckWithoutPlayerCards)
    }
}
