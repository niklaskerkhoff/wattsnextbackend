package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EffectBase
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModificationBase
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifiedValue
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

    val progressPointsDelta: Int = 0,

    val energyTargetsPerPhase: List<Map<Technology, Int>>,
    val pointTargetsPerPhase: List<Int>,
    val phaseSnapshots: List<PhaseSnapshot> = emptyList(),
    val numberOfPhases: Int,
    val numberOfTurnsPerPhase: Int,

//    val frozenPhaseValues: List<PhaseValue> = emptyList(),
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

    fun getAllProgressCards(): List<ProgressCard?> = technologyBoard.getAllCurrentProgressCards() + climateCards

    fun getAllCards(): List<Card?> =
        technologyBoard.getAllCurrentProgressCards() + climateCards + standardEventCards + catastropheEventCard

    fun getPlayableHandcards(targetPosition: Int? = null): List<ProgressCard?> =
        players
            .flatMap { it.progressCards }
            .filter { it?.isPlayable(targetPosition) ?: false }

    fun getGeneration(): Int = technologyBoard.generationCards.sumModifiedSupply()

    fun getDistribution(): Int = technologyBoard.distributionCards.sumModifiedSupply()

    fun getStorage(): Int = technologyBoard.storageCards.sumModifiedSupply()

    fun fulfillsConditions(progressCard: ProgressCard): Boolean {
        val playedCards = getAllProgressCards().filterNotNull()
        val (energy, achievements) = calculateTotalSupply(playedCards)
        return canUseSystemPoints(progressCard.supplyRequirementsForSystem.modified(progressCard, this, this), energy, achievements)
    }

    fun doesElectricityExist(): Boolean = formExists(EnergyForm.Electricity)

    fun doesHeatExist(): Boolean = formExists(EnergyForm.Heat)

    fun calculateProgressPointInfo(): ProgressPointInfo {
        val progressCards = getAllProgressCards().filterNotNull()
        val (energy, achievements) = calculateTotalSupply(progressCards)

        val cardResult = getBaseAndSystemProgressCards(progressCards, energy, achievements)

        return ProgressPointInfo(
            baseCards = cardResult.baseCards,
            systemCards = cardResult.systemCards,
            progressPoints = cardResult.progressPoints + progressPointsDelta,
        )
    }

    fun calculateProgressPoints(card: ProgressCard, modified: Boolean): ProgressPoints {
        val playedCards = getAllProgressCards().filterNotNull()
        val (energy, achievements) = calculateTotalSupply(playedCards)

        val conditions = if (modified) {
            card.supplyRequirementsForSystem.modified(card)
        } else {
            card.supplyRequirementsForSystem.base
        }

        val fulfilled = canUseSystemPoints(
            conditions,
            energy,
            achievements
        )

        return ProgressPoints(
            basePoints = card.basePoints,
            systemPoints = card.systemPoints,
            conditions = conditions,
            conditionsFulfilled = fulfilled
        )
    }

    fun withAdditionalProgressPoints(delta: Int): Game = copy(progressPointsDelta = delta)

    fun withUpdatedMoney(delta: Int): Game = copy(money = money + delta)

    fun withUpdatedResources(delta: Int): Game = copy(resources = resources + delta)

    fun withUpdatedState(state: GameState): Game = copy(state = state)

    fun withUpdatedGenerationAndDistributionTargets(delta: Int): Game =
        copy(
            energyTargetsPerPhase = energyTargetsPerPhase.mapIndexed { index, phaseTargets ->
                if (index != phase) {
                    phaseTargets
                } else {
                    mapOf(
                        Technology.Generation to (phaseTargets[Technology.Generation] ?: 0) + delta,
                        Technology.Distribution to (phaseTargets[Technology.Distribution] ?: 0) + delta,
                        Technology.Storage to (phaseTargets[Technology.Storage] ?: 0)
                    )
                }
            }
        )

    fun withNuclearCatastrophe(): Game = copy(
        technologyBoard = technologyBoard.withNuclearCatastrophe(),
    )

    fun prepare(): Result<Unit> {
        val (drawnCard, updatedStandardEventCardDeck) = standardEventCardDeck.removedLast()
        val updatedStandardEventCards = standardEventCards + drawnCard

        val (gameAfterStandardEffect, standardEffectInfos) = drawnCard.effect(this)

        return Result(
            gameAfterStandardEffect.copy(
                standardEventCards = updatedStandardEventCards,
                standardEventCardDeck = updatedStandardEventCardDeck,
            ),
            BaseInfo(gotNewStandardEventCard = true),
            cardEffectInfos = standardEffectInfos,
        )
    }

    override fun provideModifiers() = getAllCards().filterNotNull()

    override fun withNextTurn(): Result<Unit> =
        (turnInPhase + 1).let { nextTurnInPhase ->
            // Do not draw event cards during the phase as this is too confusing at the moment
            /*
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
            } else */
            if (nextTurnInPhase < numberOfTurnsPerPhase) {
                Result(
                    copy(turnInPhase = nextTurnInPhase),
                    BaseInfo()
                )
            } else {
                handleNextPhase(phase + 1)
            }
        }

    private fun getBaseAndSystemProgressCards(
        progressCards: List<ProgressCard>,
        totalEnergySupply: Map<Technology, Map<EnergyForm, Int>>,
        totalAchievementsSupply: Set<Supply.Achievement>,
    ): ProgressPointInfo {

        var progressPoints = 0
        var systemCards = listOf<ProgressCard>()
        var baseCards = listOf<ProgressCard>()

        for (progressCard in progressCards) {
            if (canUseSystemPoints(
                    progressCard.supplyRequirementsForSystem.modified(progressCard),
                    totalEnergySupply,
                    totalAchievementsSupply
                )
            ) {
                systemCards += progressCard
                progressPoints += progressCard.systemPoints
            } else {
                baseCards += progressCard
                progressPoints += progressCard.basePoints
            }
        }

        return ProgressPointInfo(
            baseCards = baseCards,
            systemCards = systemCards,
            progressPoints = progressPoints
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

    // expert mode
    /*
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
    }*/

    private fun calculateTotalSupply(
        progressCards: List<ProgressCard>
    ): Pair<Map<Technology, Map<EnergyForm, Int>>, Set<Supply.Achievement>> {

        val supplies = progressCards.map { it.supply.modified(it, this@Game, Pair(this@Game, it.getPosition() ?: -1)) }

        val totalEnergySupplies = mutableMapOf<Technology, MutableMap<EnergyForm, Int>>()
        val totalAchievementSupplies = mutableSetOf<Supply.Achievement>()

        for (supply in supplies) {
            when (supply) {
                is Supply.Achievement -> totalAchievementSupplies += supply
                is Supply.Energy -> {
                    val formMap = totalEnergySupplies.getOrPut(supply.technology) { mutableMapOf() }
                    formMap[supply.form] = formMap.getOrPut(supply.form) { 0 } + supply.size
                }

                else -> {}
            }
        }

        return Pair(totalEnergySupplies, totalAchievementSupplies)
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
                ),
                actionInfo = null,
                cardEffectInfos = emptyList(),
            )
        } else {
            val (drawnStandardEventCard, updatedStandardEventCardDeck) = standardEventCardDeck.removedLast()
            val updatedStandardEventCards = listOf(drawnStandardEventCard)

            val phaseSnapshot = createPhaseSnapshot(phase)

            val gameAfterMoneyEarned: Game = copy(money = money + phaseSnapshot.moneyEarned, phase = nextPhase)

            val (drawnCatastropheCard, updatedCatastropheEventCardDeck) =
                if (phaseSnapshot.targetsFulfilled) Pair(null, catastropheEventCardDeck)
                else catastropheEventCardDeck.removedLast()

            val (gameAfterStandardEffect, standardEffectInfos) = drawnStandardEventCard.effect(gameAfterMoneyEarned)
            val (gameAfterCatastropheEffect, catastropheEffectInfos) =
                drawnCatastropheCard?.effect(gameAfterStandardEffect) ?: Pair(gameAfterStandardEffect, emptyList())

            Result(
                gameAfterCatastropheEffect.copy(
                    turnInPhase = 0,
                    phaseSnapshots = phaseSnapshots + phaseSnapshot,
                    standardEventCards = updatedStandardEventCards,
                    standardEventCardDeck = updatedStandardEventCardDeck,
                    catastropheEventCardDeck = updatedCatastropheEventCardDeck,
                    catastropheEventCard = drawnCatastropheCard
                ),
                BaseInfo(
                    phaseCompleted = true,
                    gotNewStandardEventCard = true,
                    requirementsFulfilled = phaseSnapshot.targetsFulfilled
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

    private fun createPhaseSnapshot(phaseIndex: Int): PhaseSnapshot {
        val moneyEarned =
            minOf(
                minOf(getGeneration(), getDistribution()),
                minOf(
                    energyTargetsPerPhase[phaseIndex][Technology.Generation]!!,
                    energyTargetsPerPhase[phaseIndex][Technology.Distribution]!!
                )
            ) +
                    minOf(
                        getStorage(),
                        energyTargetsPerPhase[phaseIndex][Technology.Storage]!!
                    )

        return PhaseSnapshot(
            generation = getGeneration(),
            distribution = getDistribution(),
            storage = getStorage(),
            progressPoints = calculateProgressPointInfo().progressPoints,
            electricity = doesElectricityExist(),
            heat = doesHeatExist(),
            moneyEarned = moneyEarned,
            targetsFulfilled = hasReachedTargets(),
        )
    }

    private fun <T> ModifiedValue<T, ModificationBase>.modified(modifiedCard: ProgressCard) =
        modified(modifiedCard, this@Game, this@Game)

    private fun List<List<ProgressCard.TechnologyCard>>.sumModifiedSupply(): Int =
        sumOf { it.lastOrNull()?.getModifiedSupply()?.size ?: 0 }

    private fun formExists(form: EnergyForm): Boolean =
        technologyBoard.generationCards.any {
            it.lastOrNull()?.let { card -> card.supply.base.form == form } ?: false
        }

    private fun ProgressCard.TechnologyCard.getModifiedSupply(): Supply.Energy =
        supply.modified(this, this@Game, Pair(this@Game, getPosition()!!))

    private fun ProgressCard.getPosition(): Int? =
        when (this) {
            is ProgressCard.TechnologyCard -> technologyBoard.getPositionOf(this)
            is ProgressCard.ClimateCard -> null
        }

    private fun ProgressCard.isPlayable(targetPosition: Int? = null): Boolean {
        val positions: List<Int> =
            if (targetPosition != null) listOf(targetPosition)
            else if (this is ProgressCard.TechnologyCard) listOf(0,1,2)
            else listOf(-1)

        return positions.any { position ->
            val modifiedMoney = moneyCosts.modified(this, this@Game, Pair(this@Game, position))
            val modifiedResources = resourceCosts.modified(this, this@Game, Pair(this@Game, position))

            modifiedMoney <= this@Game.money && modifiedResources <= this@Game.resources
        }
    }

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

    data class PhaseSnapshot(
        val generation: Int,
        val distribution: Int,
        val storage: Int,
        val progressPoints: Int,
        val electricity: Boolean,
        val heat: Boolean,
        val moneyEarned: Int,
        val targetsFulfilled: Boolean,
    )

    data class ProgressPoints (
        val basePoints: Int,
        val systemPoints: Int,
        val conditions: List<Supply>,
        val conditionsFulfilled: Boolean
    )
}
