package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import java.util.UUID

class GameInit(
    val mode: Mode,
    creatorName: String,
) {
    val id: UUID = UUID.randomUUID()
    val state = "Preparing"

    private val _players: MutableList<PlayerInit> = mutableListOf(PlayerInit(creatorName))

    fun addPlayer(playerName: String) {
        var nameKey = 2
        var name = playerName

        while (_players.any { it.name == playerName }) {
            name += " $nameKey"
            nameKey++
        }

        _players.add(PlayerInit(playerName))
    }

    val players get() = _players.toList()

    enum class Mode {
        StartWithNuclear,
        StartWithCoal
    }
}

class PlayerInit(
    val name: String,
) {
    val id: UUID = UUID.randomUUID()
}
