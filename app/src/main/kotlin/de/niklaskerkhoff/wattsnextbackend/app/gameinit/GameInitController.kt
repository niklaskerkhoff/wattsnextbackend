package de.niklaskerkhoff.wattsnextbackend.app.gameinit

import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/game-init")
class GameInitController(private val gameInitService: GameInitService) {
    @PostMapping("create")
    fun createGame(@RequestBody request: CreateGameRequest): GameInitWithPlayerIdResponse =
        gameInitService.createGame(request)

    @PostMapping("join")
    fun joinGame(@RequestBody request: JoinGameRequest): GameInitWithPlayerIdResponse =
        gameInitService.joinGame(request.gameId, request.playerName)

    @PostMapping("leave")
    fun leaveGame(@RequestBody request: LeaveGameRequest): Boolean =
        gameInitService.leaveGameBeforeStart(request.gameId, request.playerId)

    @PostMapping("start")
    fun startGame(@RequestBody request: StartGameRequest) {
        gameInitService.startGame(request.gameId)
    }

    @DeleteMapping("cancel")
    fun cancelGame(@RequestBody request: CancelGameRequest): Boolean {
        return gameInitService.cancelGame(request.gameId, request.playerId)
    }

    @GetMapping("{gameId}")
    fun getGameState(@PathVariable gameId: UUID): Any = gameInitService.getGameState(gameId)

}
