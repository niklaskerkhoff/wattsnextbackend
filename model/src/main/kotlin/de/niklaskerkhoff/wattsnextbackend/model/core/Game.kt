package de.niklaskerkhoff.wattsnextbackend.model.core

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.Card
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.EnergyForm
import de.niklaskerkhoff.wattsnextbackend.model.core.energy.Technology

data class Game(
    val players: List<Player>,

    val money: Int,
    val resources: Int,

    val technologyBoard: TechnologyBoard,
    val climateCards: List<ProgressCard.ClimateCard>,

    val progressCardDeck: List<ProgressCard>,
    val standardEventCardDeck: List<EventCard>,
    val catastropheEventCardDeck: List<EventCard>,

    val standardEventCard: EventCard? = null,
    val catastropheEventCard: EventCard? = null,

    private val demandTargetsPerPhase: List<Map<Technology, Int>>,
    private val pointTargetsPerPhase: List<Int>,
    private val numberOfPhases: Int,
    private val numberOfMovesPerPhase: Int,
) {
    val phase = 0
    val moveInPhase = 0
    private val totalMove = moveInPhase * phase

    val currentPlayer get() = players[totalMove % players.size]

    val progressPoints get() = calculateProgressPoints()

    fun calculateProgressPoints(): Int {
        val progressCards = getAllProgressCards().filterNotNull()
        val totalEnergyOutputs = calculateTotalEnergyOutputs(progressCards)

        return 0
    }

    /*    private fun getBaseAndSystemProgressCards(
            progressCards: List<ProgressCard>,
            totalEnergySupply: Map<Technology, Map<EnergyForm, Int>>,
            totalIconSupply: Map<String, Int>,
        ): Triple<List<ProgressCard>, List<ProgressCard>, Int> {
            val n = progressCards.size
            var maxPoints = 0
            var bestSystemCards = listOf<ProgressCard>()
            var bestBaseCards = listOf<ProgressCard>()

            // Alle möglichen Kombinationen (2^n)
            for (mask in 0 until (1 shl n)) {
                val systemCards = mutableListOf<ProgressCard>()
                val baseCards = mutableListOf<ProgressCard>()

                // Ressourcenverbrauch vorbereiten
                val usedEnergy = mutableMapOf<Technology, MutableMap<EnergyForm, Int>>()
                val usedIcons = mutableMapOf<String, Int>()
                var valid = true

                // Versuche: Karte i als Systemkarte, wenn Bit gesetzt ist
                for (i in 0 until n) {
                    val card = progressCards[i]
                    if ((mask and (1 shl i)) != 0) {
                        // Versuche, die Systembedingungen zu erfüllen
                        if (canFulfillConditions(
                                card.values.energyRequirementsForSystem,
                                totalEnergySupply,
                                totalIconSupply,
                                usedEnergy,
                                usedIcons
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
            totalIcons: Map<String, Int>,
            usedEnergy: MutableMap<Technology, MutableMap<EnergyForm, Int>>,
            usedIcons: MutableMap<String, Int>,
        ): Boolean {
            for (condition in conditions) {
                when (condition) {
                    is Supply.Never -> return false

                    is Supply.Icon -> {
                        val used = usedIcons.getOrDefault(condition.iconName, 0)
                        val available = totalIcons.getOrDefault(condition.iconName, 0)
                        if (used >= available) return false
                        usedIcons[condition.iconName] = used + 1
                    }

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

    fun getCurrentProgressCard()

    fun getAllProgressCards(): List<ProgressCard?> = technologyBoard.getAllCurrentProgressCards() + climateCards

    fun getAllCards(): List<Card?> =
        technologyBoard.getAllCurrentProgressCards() + climateCards + standardEventCard + catastropheEventCard

    private fun calculateTotalEnergyOutputs(progressCards: List<ProgressCard>): Map<Technology, Map<EnergyForm, Int>> {
        val energyOutputs = progressCards.map { it.values.energyOutput }
        return energyOutputs.fold(mutableMapOf()) { acc, energy ->
            val formMap = acc.getOrPut(energy.technology) { mutableMapOf() } as MutableMap
            formMap[energy.form] = formMap.getOrDefault(energy.form, 0) + energy.size
            acc
        }
    }
}
