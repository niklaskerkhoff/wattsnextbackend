package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EffectBase
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModificationBase
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifiedValue
import de.niklaskerkhoff.wattsnextbackend.model.lib.partitionByType
import de.niklaskerkhoff.wattsnextbackend.model.lib.removedLast
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology
import java.util.*
import kotlin.random.Random

data class Game(
    private val id: UUID,
    val state: GameState,
    val players: List<Player>,

    val money: Int,
    val resources: Int,

    override val technologyBoard: TechnologyBoard,
    val climateCards: List<ProgressCard.ClimateCard>,

    val progressCardDeck: List<ProgressCard>,
    val standardEventCardDeck: List<EventCard>,
    val catastropheEventCardDeck: List<EventCard>,

    val standardEventCards: List<EventCard> = emptyList(),
    val catastropheEventCard: EventCard? = null,

    val phase: Int = 0,
    val turnInPhase: Int = 0,

    // TODO: What is this?
    val progressPointsDelta: Int = 0,

    val energyTargetsPerPhase: List<Map<Technology, Int>>,
    val pointTargetsPerPhase: List<Int>,
    val numberOfPhases: Int,
    val numberOfTurnsPerPhase: Int,
) : ModificationBase, EffectBase {


    val secondEventCardTurnInPhase = Random.nextInt(2, numberOfTurnsPerPhase - 2)

    private val totalMove = numberOfTurnsPerPhase * phase + turnInPhase

    val currentPlayer get() = players[totalMove % players.size]

    val publicId get() = id

//    val totalSupply get() = calculateTotalSupply()

    init {
        require(energyTargetsPerPhase.size == numberOfPhases)
        require(pointTargetsPerPhase.size == numberOfPhases)
    }

    override fun provideModifiers() = getAllCards().filterNotNull()

    fun withAdditionalProgressPoints(delta: Int): Game = copy(progressPointsDelta = delta)

    fun withUpdatedMoney(delta: Int): Game = copy(money = money + delta)

    fun withUpdatedResources(delta: Int): Game = copy(resources = resources + delta)

    fun withUpdatedState(state: GameState): Game = copy(state = state)

    fun getAllProgressCards(): List<ProgressCard?> = technologyBoard.getAllCurrentProgressCards() + climateCards

    fun getAllCards(): List<Card?> =
        technologyBoard.getAllCurrentProgressCards() + climateCards + standardEventCards + catastropheEventCard

    // TODO: Check for end of phase?
    override fun withNextTurn(): Result<Unit> =
        (turnInPhase + 1).let { nextTurnInPhase ->
            if (nextTurnInPhase == secondEventCardTurnInPhase) {
                val (drawnCard, updatedStandardEventCardDeck) = standardEventCardDeck.removedLast()
                val updatedStandardEventCards = standardEventCards + drawnCard

                val (gameAfterStandardEffect, standardEffectInfos) = drawnCard.effect(this)

                Result(
                    gameAfterStandardEffect.copy(
                        turnInPhase = nextTurnInPhase,
                        standardEventCards = updatedStandardEventCards,
                        standardEventCardDeck = updatedStandardEventCardDeck,
                    ),
                    BaseInfo(gotNewStandardEventCard = true),
                    cardEffectInfos = standardEffectInfos,
                )
            } else if (nextTurnInPhase < numberOfTurnsPerPhase) {
                Result(
                    copy(turnInPhase = nextTurnInPhase),
                    BaseInfo()
                )
            } else {
                handleNextPhase(phase + 1)
            }
        }


    fun calculateProgressPointInfo(): ProgressPointInfo {
        val progressCards = getAllProgressCards().filterNotNull()
        val (energy, achievements) = calculateTotalSupply(progressCards)

        val (technologyCards, climateCards) =
            progressCards.partitionByType<ProgressCard.TechnologyCard, ProgressCard.ClimateCard>()

        val technologyResult = getBaseAndSystemTechnologyCards(technologyCards, energy, achievements)
        val climateResult = getBaseAndSystemClimateCards(climateCards, energy, achievements)

        return ProgressPointInfo(
            baseCards = technologyResult.baseCards + climateResult.baseCards,
            systemCards = technologyResult.systemCards + climateResult.systemCards,
            progressPoints = technologyResult.progressPoints + climateResult.progressPoints + progressPointsDelta,
        )
    }

    private fun getBaseAndSystemClimateCards(
        climateCards: List<ProgressCard.ClimateCard>,
        totalEnergySupply: Map<Technology, Map<EnergyForm, Int>>,
        totalAchievementsSupply: Set<Supply.Achievement>,
    ): ProgressPointInfo {

        var climateProgressPoints = 0
        var systemCards = listOf<ProgressCard>()
        var baseCards = listOf<ProgressCard>()

        for (climateCard in climateCards) {
            if (canUseSystemPoints(
                    climateCard.supplyRequirementsForSystem.modified(climateCard),
                    totalEnergySupply,
                    totalAchievementsSupply
                )
            ) {
                systemCards += climateCard
                climateProgressPoints += climateCard.systemPoints
            } else {
                baseCards += climateCard
                climateProgressPoints += climateCard.basePoints
            }
        }


        return ProgressPointInfo(
            baseCards = baseCards,
            systemCards = systemCards,
            progressPoints = climateProgressPoints
        )
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
    ): ProgressPointInfo {
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
                            card.supplyRequirementsForSystem.modified(card),
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
            val points =
                systemCards.sumOf { it.systemPoints } + baseCards.sumOf { it.basePoints }
            if (points > maxPoints) {
                maxPoints = points
                bestSystemCards = systemCards
                bestBaseCards = baseCards
            }
        }

        return ProgressPointInfo(
            baseCards = bestBaseCards,
            systemCards = bestSystemCards,
            progressPoints = maxPoints
        )
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
            val updatedState = if (requirementsFulfilled) GameState.Won else GameState.Lost
            Result(
                copy(state = updatedState),
                baseInfo = BaseInfo(
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

            val (gameAfterStandardEffect, standardEffectInfos) = drawnStandardEventCard.effect(this)
            val (gameAfterCatastropheEffect, catastropheEffectInfos) =
                drawnCatastropheCard?.effect(gameAfterStandardEffect) ?: Pair(gameAfterStandardEffect, emptyList())

            Result(
                gameAfterCatastropheEffect.copy(
                    turnInPhase = 0,
                    phase = nextPhase,
                    standardEventCards = updatedStandardEventCards,
                    standardEventCardDeck = updatedStandardEventCardDeck,
                    catastropheEventCardDeck = updatedCatastropheEventCardDeck,
                    catastropheEventCard = drawnCatastropheCard
                ),
                BaseInfo(
                    phaseCompleted = true,
                    gotNewStandardEventCard = true,
                    requirementsFulfilled = requirementsFulfilled
                ),
                cardEffectInfos = standardEffectInfos + catastropheEffectInfos
            )
        }

    private fun hasReachedTargets(): Boolean {
        val pointTarget = pointTargetsPerPhase[phase]
        if (calculateProgressPointInfo().progressPoints < pointTarget) return false

        val supplyTarget = energyTargetsPerPhase[phase]
        val totalSupply = calculateTotalSupply(getAllProgressCards().filterNotNull())
        val technologySupply = totalSupply.first.mapValues { (_, energyMap) -> energyMap.values.sum() }
        supplyTarget.forEach { (technology, size) -> if ((technologySupply[technology] ?: 0) < size) return false }

        return true
    }

    private fun <T> ModifiedValue<T, ModificationBase>.modified(modifiedCard: ProgressCard) =
        modified(modifiedCard, this@Game, this@Game)

    data class BaseInfo(
        val phaseCompleted: Boolean = false,
        val gotNewStandardEventCard: Boolean = false,
        val hasGameStateChanged: Boolean = false,
        val requirementsFulfilled: Boolean? = null,
    )

    data class ProgressPointInfo(
        val baseCards: List<ProgressCard>,
        val systemCards: List<ProgressCard>,
        val progressPoints: Int,
    )
}
