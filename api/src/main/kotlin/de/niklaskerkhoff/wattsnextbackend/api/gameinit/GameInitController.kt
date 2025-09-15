package de.niklaskerkhoff.wattsnextbackend.api.gameinit

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("game-management")
class GameInitController(private val gameInitService: GameInitService) {
    @PostMapping
    fun createGame(@RequestBody request: CreateGameRequest): CreateGameResponse = gameInitService.createGame(request)

    @PostMapping("join")
    fun joinGame(@RequestBody request: JoinGameRequest): String =
        gameInitService.joinGame(request.gameId, request.playerName)

    @PostMapping("start")
    fun startGame(@RequestBody startGameRequest: StartGameRequest) {
        gameInitService.startGame(startGameRequest.gameId)
    }
}
