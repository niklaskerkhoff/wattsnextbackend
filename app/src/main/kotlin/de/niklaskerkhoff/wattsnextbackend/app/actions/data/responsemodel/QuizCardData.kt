package de.niklaskerkhoff.wattsnextbackend.app.actions.data.responsemodel

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.QuizCard
import java.util.UUID

/**
 * A pending quiz as sent to clients. Deliberately omits `correctIndex` and `explanation` so the
 * answer cannot be read from the network traffic before the team has answered — those are only
 * revealed in the answer response (see AnswerQuizAction.Info).
 */
data class QuizCardData(
    val id: UUID,
    val question: String,
    val options: List<String>,
    val info: String?,
) {
    constructor(quizCard: QuizCard) : this(
        id = quizCard.id,
        question = quizCard.question,
        options = quizCard.options,
        info = quizCard.info,
    )
}
