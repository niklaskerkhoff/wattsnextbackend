package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import de.niklaskerkhoff.wattsnextbackend.api.actions.EntityResolver
import de.niklaskerkhoff.wattsnextbackend.api.gameinit.GameInit.Mode.START_WITH_COAL
import de.niklaskerkhoff.wattsnextbackend.api.gameinit.GameInit.Mode.START_WITH_NUCLEAR
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

object GameBuilder {
    fun buildGame(gameInit: GameInit): Pair<Game, EntityResolver> {

        val startTechnologyCard =
            when (gameInit.mode) {
                START_WITH_COAL -> technologyCards.first { it.tags.contains(Tag.Coal.name) }
                START_WITH_NUCLEAR -> technologyCards.first { it.tags.contains(Tag.Nuclear.name) }
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
                generationCards = listOf(listOf(startTechnologyCard)),
                distributionCards = emptyList(),
                storageCards = emptyList(),
            ),
            players = gameInit.playerNames.map {
                Player(
                    name = it,
                    progressCards = emptyList()
                )
            },
            climateCards = emptyList(),
            progressCardDeck = progressCardDeckWithoutStartCard,
            standardEventCardDeck = standardEventCardDeck,
            catastropheEventCardDeck = catastropheEventCardDeck,
            energyTargetsPerPhase = emptyList(),
            pointTargetsPerPhase = emptyList(),
            numberOfPhases = 3,
            numberOfTurnsPerPhase = 12,
        )

        return Pair(game, EntityResolver(players))
    }

    private fun buildPlayers(
        gameInit: GameInit,
        progressCardDeck: List<ProgressCard>
    ): Pair<List<Player>, List<ProgressCard>> {
        val playerCount = gameInit.playerNames.size
        val cardsPerPlayer = when (playerCount) {
            2 -> 5
            3 -> 4
            else -> 3
        }
        val totalPlayerCardCount = cardsPerPlayer * playerCount

        val playerCards = progressCardDeck.take(totalPlayerCardCount)
        val progressCardDeckWithoutPlayerCards = progressCardDeck.drop(totalPlayerCardCount)

        val players = gameInit.playerNames.mapIndexed { index, name ->
            Player(
                name = name,
                progressCards = playerCards.slice(index * cardsPerPlayer until (index + 1) * cardsPerPlayer)
            )
        }

        return Pair(players, progressCardDeckWithoutPlayerCards)
    }
}
