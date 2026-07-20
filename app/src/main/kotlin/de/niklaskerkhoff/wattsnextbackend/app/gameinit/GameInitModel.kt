package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import de.niklaskerkhoff.wattsnextbackend.model.core.GameState
import java.util.UUID

class GameInit(
    val mode: Mode,
    creatorName: String,
) {
    val id: UUID = UUID.randomUUID()
    var state = GameState.Preparing

    private val _players: MutableList<PlayerInit> = mutableListOf(PlayerInit(creatorName))

    fun addPlayer(playerName: String) {
        var nameKey = 2
        var name = playerName

        while (_players.any { it.name == name }) {
            name = "$playerName $nameKey"
            nameKey++
        }

        _players.add(PlayerInit(name))
    }

    fun removePlayer(playerId: UUID): Boolean {
        val player = _players.find { it.id == playerId } ?: return false
        _players.remove(player)
        return true
    }


    val players get() = _players.toList()

    fun getPlayer(playerId: UUID): PlayerInit? {
        return _players.find { it.id == playerId }
    }

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
