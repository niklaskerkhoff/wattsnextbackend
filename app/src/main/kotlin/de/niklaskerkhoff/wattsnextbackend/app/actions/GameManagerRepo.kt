package de.niklaskerkhoff.wattsnextbackend.app.actions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import de.niklaskerkhoff.wattsnextbackend.model.core.Player
import de.niklaskerkhoff.wattsnextbackend.model.core.TechnologyBoard
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@Component
class GameManagerRepo {
    private val log = LoggerFactory.getLogger(javaClass)

    private val mapper = jacksonObjectMapper()

    private val gameManagers: MutableMap<UUID, GameManager> = ConcurrentHashMap()

    fun getGameManager(gameId: UUID): GameManager? {
        return gameManagers[gameId]
    }

    fun addGameManager(gameManager: GameManager) {
        gameManagers[gameManager.game.publicId] = gameManager
    }

    fun removeGameManager(gameId: UUID) {
        gameManagers.remove(gameId)
    }

    @Scheduled(fixedRate = /*1000 * 10*/ 1000 * 3600 * 24) // run daily
    fun cleanup() {
        println("cleanup")
        val lifetimeAfterLastAction = /*1000 * 5*/ 1000 * 3600 * 24 * 14 // two weeks
        val forceQuitTime = System.currentTimeMillis() - lifetimeAfterLastAction
        val quitGameStates = listOf(GameState.Cancelled, GameState.Won, GameState.Lost)

        gameManagers.keys.forEach { gameId ->
            val gameManager = gameManagers[gameId] ?: throw IllegalStateException("Game manager not found.")

            if (gameManager.lastActionTime < forceQuitTime || gameManager.game.state in quitGameStates) {
                removeGameManager(gameId)
            }
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun load() {
        log.info("Loading game managers from file.")
        val path = Paths.get(STORE_PATH)

        if (!path.toFile().exists()) return

        val raw = path.toFile().readText()
        val data = mapper.readTree(raw)


        val loadedGameManagers: List<GameManager> =
            data.mapNotNull { game ->
                try {

                val tempEntityResolver = EntityResolver(emptyList())

                val lastActionTime = game.get("lastActionTime")?.asLong() ?: System.currentTimeMillis()

                val loadedGame = Game(
                    id = UUID.fromString(game.get("id").asText()),
                    state = GameState.valueOf(game.get("state").asText()),
                    players = game.get("players").map {
                        Player(
                            id = UUID.fromString(it.get("id").asText()),
                            name = it.get("name").asText(),
                            progressCards = it.get("progressCards").map { card ->
                                card?.let { tempEntityResolver.getProgressCard(UUID.fromString(card.asText())) }
                            }
                        )
                    },

                    money = game.get("money").asInt(),
                    resources = game.get("resources").asInt(),

                    technologyBoard = TechnologyBoard(
                        generationCards = game.get("technologyBoard").get("generationCards")
                            .map { it.map { card -> tempEntityResolver.getTechnologyCard(UUID.fromString(card.asText()))!! } },
                        distributionCards = game.get("technologyBoard").get("distributionCards")
                            .map { it.map { card -> tempEntityResolver.getTechnologyCard(UUID.fromString(card.asText()))!! } },
                        storageCards = game.get("technologyBoard").get("storageCards")
                            .map { it.map { card -> tempEntityResolver.getTechnologyCard(UUID.fromString(card.asText()))!! } },
                    ),
                    climateCards = game.get("climateCards")
                        .map { tempEntityResolver.getClimateCard(UUID.fromString(it.asText()))!! },

                    progressCardDeck = game.get("progressCardDeck")
                        .map { tempEntityResolver.getProgressCard(UUID.fromString(it.asText()))!! },
                    progressCardDiscardPile = game.get("progressCardDiscardPile")
                        ?.map { tempEntityResolver.getProgressCard(UUID.fromString(it.asText()))!! }
                        ?: emptyList(),
                    upcomingProgressCards = game.get("upcomingProgressCards")
                        ?.map { tempEntityResolver.getProgressCard(UUID.fromString(it.asText()))!! }
                        ?: emptyList(),
                    standardEventCardDeck = game.get("standardEventCardDeck")
                        .map { tempEntityResolver.getEventCard(UUID.fromString(it.asText()))!! },
                    catastropheEventCardDeck = game.get("catastropheEventCardDeck")
                        .map { tempEntityResolver.getEventCard(UUID.fromString(it.asText()))!! },

                    standardEventCards = game.get("standardEventCards")
                        .map { tempEntityResolver.getEventCard(UUID.fromString(it.asText()))!! },

                    catastropheEventCard = game.get("catastropheEventCard")
                        ?.takeUnless { it.isNull }
                        ?.let { tempEntityResolver.getEventCard(UUID.fromString(it.asText())) },

                    phase = game.get("phase").asInt(),
                    turnInPhase = game.get("turnInPhase").asInt(),

                    progressPointsDelta = game.get("progressPointsDelta").asInt(),

                    energyTargetsPerPhase = game.get("energyTargetsPerPhase").map { phaseNode ->
                        phaseNode.properties().associate { entry ->
                            Technology.valueOf(entry.key) to entry.value.asInt()
                        }
                    },

                    pointTargetsPerPhase = game.get("pointTargetsPerPhase").map { it.asInt() },

                    phaseSnapshots = game.get("phaseSnapshots")?.map { snapshot ->
                        Game.PhaseSnapshot(
                            generation = snapshot.get("generation").asInt(),
                            distribution = snapshot.get("distribution").asInt(),
                            storage = snapshot.get("storage").asInt(),
                            progressPoints = snapshot.get("progressPoints").asInt(),
                            electricity = snapshot.get("electricity").asBoolean(),
                            heat = snapshot.get("heat").asBoolean(),
                            moneyEarned = snapshot.get("moneyEarned").asInt(),
                            targetsFulfilled = snapshot.get("targetsFulfilled").asBoolean(),
                        )
                    } ?: emptyList(),

                    numberOfPhases = game.get("numberOfPhases").asInt(),
                    numberOfTurnsPerPhase = game.get("numberOfTurnsPerPhase").asInt(),

                    secondEventCardTurnInPhase = game.get("secondEventCardTurnInPhase")
                        ?.takeUnless { it.isNull }
                        ?.asInt()
                        ?: Random.nextInt(2, game.get("numberOfTurnsPerPhase").asInt() - 2),
                )

                val withPlayersEntityResolver = EntityResolver(loadedGame.players)

                GameManager(loadedGame, withPlayersEntityResolver, lastActionTime)
                } catch (e: Exception) {
                    log.warn(
                        "Skipping game that could not be loaded (id={}): {}",
                        game.get("id")?.asText(), e.toString()
                    )
                    null
                }
            }

        loadedGameManagers.forEach { gameManager ->
            addGameManager(gameManager)
        }
    }

    @PreDestroy
    fun store() {
        log.info("Storing game managers to file.")
        val path = Paths.get(STORE_PATH)

        path.parent?.let { Files.createDirectories(it) }

        val gameManagerData = gameManagers.map { gameManager ->
            with(gameManager.value.game) {
                mapOf(
                    "lastActionTime" to gameManager.value.lastActionTime,
                    "id" to publicId,
                    "state" to state,
                    "players" to players.map {
                        mapOf(
                            "id" to it.publicId,
                            "name" to it.name,
                            "progressCards" to it.progressCards.map { card -> card?.publicId }
                        )
                    },

                    "money" to money,
                    "resources" to resources,

                    "technologyBoard" to mapOf(
                        "generationCards" to technologyBoard.generationCards.map { it.map { card -> card.publicId } },
                        "distributionCards" to technologyBoard.distributionCards.map { it.map { card -> card.publicId } },
                        "storageCards" to technologyBoard.storageCards.map { it.map { card -> card.publicId } },
                    ),
                    "climateCards" to climateCards.map { it.publicId },

                    "progressCardDeck" to progressCardDeck.map { it.publicId },
                    "progressCardDiscardPile" to progressCardDiscardPile.map { it.publicId },
                    "upcomingProgressCards" to upcomingProgressCards.map { it.publicId },
                    "standardEventCardDeck" to standardEventCardDeck.map { it.publicId },
                    "catastropheEventCardDeck" to catastropheEventCardDeck.map { it.publicId },

                    "standardEventCards" to standardEventCards.map { it.publicId },
                    "catastropheEventCard" to catastropheEventCard?.publicId,

                    "phase" to phase,
                    "turnInPhase" to turnInPhase,

                    "progressPointsDelta" to progressPointsDelta,

                    "energyTargetsPerPhase" to energyTargetsPerPhase,
                    "pointTargetsPerPhase" to pointTargetsPerPhase,

                    "phaseSnapshots" to phaseSnapshots.map { snapshot ->
                        mapOf(
                            "generation" to snapshot.generation,
                            "distribution" to snapshot.distribution,
                            "storage" to snapshot.storage,
                            "progressPoints" to snapshot.progressPoints,
                            "electricity" to snapshot.electricity,
                            "heat" to snapshot.heat,
                            "moneyEarned" to snapshot.moneyEarned,
                            "targetsFulfilled" to snapshot.targetsFulfilled,
                        )
                    },

                    "numberOfPhases" to numberOfPhases,
                    "numberOfTurnsPerPhase" to numberOfTurnsPerPhase,

                    "secondEventCardTurnInPhase" to secondEventCardTurnInPhase,
                )
            }
        }

        val jsonString = mapper.writeValueAsString(gameManagerData)
        Files.writeString(path, jsonString)
    }

    companion object {
        private const val STORE_PATH = "./data/gamemanagers.json"
    }
}
