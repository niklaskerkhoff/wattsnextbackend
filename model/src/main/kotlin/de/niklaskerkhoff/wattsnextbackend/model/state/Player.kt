package de.niklaskerkhoff.wattsnextbackend.model.state

import de.niklaskerkhoff.wattsnextbackend.model.cards.ProgressCard

class Player(
    val name: String,
    initialProgressCards: List<ProgressCard?>
) {
    private val _progressCards = initialProgressCards.toMutableList()
    val progressCards: List<ProgressCard?> = _progressCards

    fun replaceProgressCard(replacementCard: ProgressCard?, cardToReplace: ProgressCard?) {
        _progressCards.indexOfFirst { it == cardToReplace }.let { index -> _progressCards[index] = replacementCard }
    }
}
