package de.niklaskerkhoff.wattsnextbackend.model.actions

import de.niklaskerkhoff.wattsnextbackend.model.core.Action
import de.niklaskerkhoff.wattsnextbackend.model.core.Game
import de.niklaskerkhoff.wattsnextbackend.model.core.Result

/**
 * Answers the currently pending quiz with the chosen option. The team's active player submits on
 * behalf of everyone. Correct earns 1 money, wrong costs 1 (see [Game.withQuizResolved]).
 */
class AnswerQuizAction(
    private val optionIndex: Int,
) : Action<AnswerQuizAction.Info>() {

    override fun canExecute(game: Game): Boolean {
        val quiz = game.pendingQuiz ?: return false
        return optionIndex in quiz.options.indices
    }

    override fun execute(game: Game): Result<Info> {
        val quiz = game.pendingQuiz!!
        val wasCorrect = optionIndex == quiz.correctIndex
        val (updatedGame, becameLost) = game.withQuizResolved(wasCorrect)

        return Result(
            game = updatedGame,
            baseInfo = Game.BaseInfo(hasGameStateChanged = becameLost),
            actionInfo = Info(
                chosenIndex = optionIndex,
                correctIndex = quiz.correctIndex,
                wasCorrect = wasCorrect,
                moneyDelta = if (wasCorrect) 1 else -1,
                explanation = quiz.explanation,
            ),
        )
    }

    data class Info(
        val chosenIndex: Int,
        val correctIndex: Int,
        val wasCorrect: Boolean,
        val moneyDelta: Int,
        val explanation: String,
    )
}
