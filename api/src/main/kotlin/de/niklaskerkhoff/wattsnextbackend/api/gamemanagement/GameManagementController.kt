package de.niklaskerkhoff.wattsnextbackend.api.gamemanagement

import de.niklaskerkhoff.wattsnextbackend.api.actions.PlayerDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("game-management")
class GameManagementController(private val gameService: GameService) {
    @PostMapping
    fun createGame(@RequestBody createGameRequest: CreateGameRequest): CreateGameResponse {
        return gameService.createGame(createGameRequest)
    }

    @PostMapping("join")
    fun joinGame(@RequestBody joinGameRequest: JoinGameRequest): PlayerDto {
        return gameService.joinGame(joinGameRequest.gameId, joinGameRequest.playerName)
    }

    @PostMapping("start")
    fun startGame(@RequestBody startGameRequest: StartGameRequest) {
        gameService.startGame(startGameRequest.gameId)
    }
}