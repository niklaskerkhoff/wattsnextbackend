package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.EnergyForm
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Supply
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Technology
import de.niklaskerkhoff.wattsnextbackend.model.lib.partitionByType
import de.niklaskerkhoff.wattsnextbackend.model.lib.removedLast
import kotlin.random.Random

data class Game(
    val state: GameState,
    val players: List<Player>,

    val money: Int,
    val resources: Int,

    val technologyBoard: TechnologyBoard,
    val climateCards: List<ProgressCard.ClimateCard>,

    val progressCardDeck: List<ProgressCard>,
    val standardEventCardDeck: List<EventCard>,
    val catastropheEventCardDeck: List<EventCard>,

    val standardEventCards: List<EventCard> = emptyList(),
    val catastropheEventCard: EventCard? = null,

    val phase: Int = 0,
    val turnInPhase: Int = 0,

    private val energyTargetsPerPhase: List<Map<Technology, Int>>,
    private val pointTargetsPerPhase: List<Int>,
    private val numberOfPhases: Int,
    private val numberOfTurnsPerPhase: Int,
) {


    val secondEventCardTurnInPhase = Random.nextInt(2, numberOfTurnsPerPhase - 2)

    private val totalMove = turnInPhase * phase

    val currentPlayer get() = players[totalMove % players.size]

    val progressPoints get() = calculateProgressPoints()

//    val totalSupply get() = calculateTotalSupply()

    init {
        require(energyTargetsPerPhase.size == numberOfPhases)
        require(pointTargetsPerPhase.size == numberOfPhases)
    }

    private fun calculateProgressPoints(): Triple<List<ProgressCard>, List<ProgressCard>, Int> {
        val progressCards = getAllProgressCards().filterNotNull()
        val (energy, achievements) = calculateTotalSupply(progressCards)

        val (technologyCards, climateCards) =
            progressCards.partitionByType<ProgressCard.TechnologyCard, ProgressCard.ClimateCard>()

        val technologyResult = getBaseAndSystemTechnologyCards(technologyCards, energy, achievements)
        val climateResult = getBaseAndSystemClimateCards(climateCards, energy, achievements)

        return Triple(
            technologyResult.first + climateResult.first,
            technologyResult.second + climateResult.second,
            technologyResult.third + climateResult.third,
        )
    }

    private fun getBaseAndSystemClimateCards(
        climateCards: List<ProgressCard.ClimateCard>,
        totalEnergySupply: Map<Technology, Map<EnergyForm, Int>>,
        totalAchievementsSupply: Set<Supply.Achievement>,
    ): Triple<List<ProgressCard>, List<ProgressCard>, Int> {

        var climateProgressPoints = 0
        var systemCards = listOf<ProgressCard>()
        var baseCards = listOf<ProgressCard>()

        for (climateCard in climateCards) {
            if (canUseSystemPoints(
                    climateCard.values.supplyRequirementsForSystem,
                    totalEnergySupply,
                    totalAchievementsSupply
                )
            ) {
                systemCards += climateCard
                climateProgressPoints += climateCard.values.systemPoints
            } else {
                baseCards += climateCard
                climateProgressPoints += climateCard.values.basePoints
            }
        }


        return Triple(baseCards, systemCards, climateProgressPoints)
    }

    private fun canUseSystemPoints(
        conditions: List<Supply>,
        totalEnergySupply: Map<Technology, Map<EnergyForm, Int>>,
        totalAchievementsSupply: Set<Supply.Achievement>,
    ): Boolean {
        for (condition in conditions) {
            when (condition) {
                is Supply.Never -> return false

                is Supply.Achievement -> if (!totalAchievementsSupply.contains(condition)) return false

                is Supply.Energy -> {
                    val available = totalEnergySupply[condition.technology]?.getOrDefault(condition.form, 0) ?: 0
                    if (condition.size > available) return false
                }
            }
        }
        return true
    }

    private fun getBaseAndSystemTechnologyCards(
        technologyCards: List<ProgressCard.TechnologyCard>,
        totalEnergySupply: Map<Technology, Map<EnergyForm, Int>>,
        totalAchievementsSupply: Set<Supply.Achievement>,
    ): Triple<List<ProgressCard>, List<ProgressCard>, Int> {
        val n = technologyCards.size
        var maxPoints = 0
        var bestSystemCards = listOf<ProgressCard>()
        var bestBaseCards = listOf<ProgressCard>()

        // Alle möglichen Kombinationen (2^n)
        for (mask in 0 until (1 shl n)) {
            val baseCards = mutableListOf<ProgressCard>()
            val systemCards = mutableListOf<ProgressCard>()

            // Ressourcenverbrauch vorbereiten
            val usedEnergy = mutableMapOf<Technology, MutableMap<EnergyForm, Int>>()
            val usedAchievements = mutableMapOf<String, Int>()
            var valid = true

            // Versuche: Karte i als Systemkarte, wenn Bit gesetzt ist
            for (i in 0 until n) {
                val card = technologyCards[i]
                if ((mask and (1 shl i)) != 0) {
                    // Versuche, die Systembedingungen zu erfüllen
                    if (canFulfillConditions(
                            card.values.supplyRequirementsForSystem,
                            totalEnergySupply,
                            totalAchievementsSupply,
                            usedEnergy,
                        )
                    ) {
                        systemCards += card
                    } else {
                        valid = false
                        break
                    }
                } else {
                    baseCards += card
                }
            }

            if (!valid) continue

            // Punkte berechnen
            val points = systemCards.sumOf { it.values.systemPoints } + baseCards.sumOf { it.values.basePoints }
            if (points > maxPoints) {
                maxPoints = points
                bestSystemCards = systemCards
                bestBaseCards = baseCards
            }
        }

        return Triple(bestBaseCards, bestSystemCards, maxPoints)
    }

    private fun canFulfillConditions(
        conditions: List<Supply>,
        totalEnergy: Map<Technology, Map<EnergyForm, Int>>,
        totalAchievements: Set<Supply.Achievement>,
        usedEnergy: MutableMap<Technology, MutableMap<EnergyForm, Int>>,
    ): Boolean {
        for (condition in conditions) {
            when (condition) {
                is Supply.Never -> return false

                is Supply.Achievement -> if (!totalAchievements.contains(condition)) return false

                is Supply.Energy -> {
                    val techMap = usedEnergy.getOrPut(condition.technology) { mutableMapOf() }
                    val used = techMap.getOrDefault(condition.form, 0)
                    val available = totalEnergy[condition.technology]?.getOrDefault(condition.form, 0) ?: 0
                    if (used + condition.size > available) return false
                    techMap[condition.form] = used + condition.size
                }
            }
        }
        return true
    }

    fun withNextTurn(): Result<Unit> =
        (turnInPhase + 1).let { nextTurnInPhase ->
            if (nextTurnInPhase == secondEventCardTurnInPhase) {
                val (drawnCard, updatedStandardEventCardDeck) = standardEventCardDeck.removedLast()
                val updatedStandardEventCards = standardEventCards + drawnCard

                Result(
                    copy(
                        turnInPhase = nextTurnInPhase,
                        standardEventCards = updatedStandardEventCards,
                        standardEventCardDeck = updatedStandardEventCardDeck,
                    ),
                    BaseInformation(gotNewStandardEventCard = true)
                )
            } else if (nextTurnInPhase < numberOfTurnsPerPhase) {
                Result(
                    copy(turnInPhase = nextTurnInPhase),
                    BaseInformation()
                )
            } else {
                handleNextPhase(phase + 1)
            }
        }

    fun getAllProgressCards(): List<ProgressCard?> = technologyBoard.getAllCurrentProgressCards() + climateCards

    fun getAllCards(): List<Card?> =
        technologyBoard.getAllCurrentProgressCards() + climateCards + standardEventCards + catastropheEventCard

    private fun calculateTotalSupply(
        progressCards: List<ProgressCard>
    ): Pair<Map<Technology, Map<EnergyForm, Int>>, Set<Supply.Achievement>> {

        val supplies = progressCards.map { it.supply }

        val energy = mutableMapOf<Technology, MutableMap<EnergyForm, Int>>()
        val achievements = mutableSetOf<Supply.Achievement>()

        for (supply in supplies) {
            when (supply) {
                is Supply.Achievement -> achievements += supply
                is Supply.Energy -> {
                    val formMap = energy.getOrPut(supply.technology) { mutableMapOf() }
                    formMap[supply.form] = formMap.getOrPut(supply.form) { 0 } + energy.size
                }

                else -> {}
            }
        }

        return Pair(energy, achievements)
    }

    private fun handleNextPhase(nextPhase: Int): Result<Unit> =
        if (nextPhase == numberOfPhases) {
            val requirementsFulfilled = hasReachedTargets()
            val updatedState = if (requirementsFulfilled) GameState.WON else GameState.LOST
            Result(
                copy(state = updatedState),
                baseInformation = BaseInformation(
                    phaseCompleted = true,
                    hasGameStateChanged = true,
                    requirementsFulfilled = requirementsFulfilled
                )
            )
        } else {
            val (drawnStandardEventCard, updatedStandardEventCardDeck) = standardEventCardDeck.removedLast()
            val updatedStandardEventCards = listOf(drawnStandardEventCard)

            val requirementsFulfilled = hasReachedTargets()

            val (drawnCatastropheCard, updatedCatastropheEventCardDeck) =
                if (requirementsFulfilled) Pair(null, catastropheEventCardDeck)
                else catastropheEventCardDeck.removedLast()

            Result(
                copy(
                    turnInPhase = 0,
                    phase = nextPhase,
                    standardEventCards = updatedStandardEventCards,
                    standardEventCardDeck = updatedStandardEventCardDeck,
                    catastropheEventCardDeck = updatedCatastropheEventCardDeck,
                    catastropheEventCard = drawnCatastropheCard
                ),
                BaseInformation(
                    phaseCompleted = true,
                    gotNewStandardEventCard = true,
                    requirementsFulfilled = requirementsFulfilled
                )
            )
        }

    private fun hasReachedTargets(): Boolean {
        val supplyTarget = energyTargetsPerPhase[phase]
        val pointTarget = pointTargetsPerPhase[phase]

        calculateTotalSupply(getAllProgressCards().filterNotNull())
        return true
    }

    data class BaseInformation(
        val phaseCompleted: Boolean = false,
        val gotNewStandardEventCard: Boolean = false,
        val hasGameStateChanged: Boolean = false,
        val requirementsFulfilled: Boolean? = null,
    )
}
