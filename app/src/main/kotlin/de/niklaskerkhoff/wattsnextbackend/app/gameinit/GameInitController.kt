package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("game-init")
class GameInitController(private val gameInitService: GameInitService) {
    @PostMapping("create")
    fun createGame(@RequestBody request: CreateGameRequest): GameInitWithPlayerIdResponse =
        gameInitService.createGame(request)

    @PostMapping("join")
    fun joinGame(@RequestBody request: JoinGameRequest): GameInitWithPlayerIdResponse =
        gameInitService.joinGame(request.gameId, request.playerName)

    @PostMapping("start")
    fun startGame(@RequestBody request: StartGameRequest) {
        gameInitService.startGame(request.gameId)
    }

    @GetMapping("{gameId}")
    fun getGameState(@PathVariable gameId: UUID): Any = gameInitService.getGameState(gameId)
}
