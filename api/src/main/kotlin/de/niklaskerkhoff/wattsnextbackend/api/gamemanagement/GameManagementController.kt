package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import de.niklaskerkhoff.wattsnextbackend.api.actions.PlayerDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("game-management")
class GameManagementController {
    @PostMapping
    fun createGame(@RequestBody createGameRequest: CreateGameRequest) {

    }

    @PostMapping("join")
    fun joinGame(@RequestBody joinGameRequest: JoinGameRequest): PlayerDto? {
        // sende GameDto an alle
        return null
    }

    @PostMapping("start")
    fun startGame() {
        // sende GameDto an alle
    }
}