package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import java.util.*

class GameInit(
    val mode: Mode,
    creatorName: String,
) {
    val id: UUID = UUID.randomUUID()
    val state = "Initializing"

    val playerNames: MutableList<String> = mutableListOf(creatorName)

    enum class Mode {
        START_WITH_NUCLEAR,
        START_WITH_COAL
    }
}
