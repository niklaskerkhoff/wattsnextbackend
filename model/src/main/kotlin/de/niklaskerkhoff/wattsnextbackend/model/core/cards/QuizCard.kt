package de.niklaskerkhoff.wattsnextbackend.model.core.cards

import java.util.UUID

/**
 * A quiz question shown twice per phase. A correct answer earns the team money, a wrong one costs it.
 * Pure data — unlike [EventCard]/[ProgressCard] it carries no modifiers or effects and is not bound
 * to a phase; a shared, shuffled deck is drawn from throughout the game.
 *
 * [correctIndex] and [explanation] must not be sent to clients before the team has answered
 * (see the response models), otherwise the answer could be read from the network traffic.
 */
data class QuizCard(
    val id: UUID,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    // Optional background text ("Was bedeutet überhaupt …?") shown alongside the question.
    val info: String? = null,
) {
    init {
        require(correctIndex in options.indices) { "correctIndex out of bounds for quiz '$question'" }
        require(options.size >= 2) { "quiz '$question' needs at least two options" }
    }
}
