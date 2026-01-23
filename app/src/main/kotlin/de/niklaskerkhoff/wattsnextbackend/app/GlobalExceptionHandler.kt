package de.niklaskerkhoff.wattsnextbackend.app

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponseException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<String?> {
        return ResponseEntity
            .status(ex.statusCode)
            .body<String?>(ex.reason ?: ex.message)
    }

    @ExceptionHandler(ErrorResponseException::class)
    fun handleErrorResponseException(ex: ErrorResponseException): ResponseEntity<String?> {
        return ResponseEntity
            .status(ex.statusCode)
            .body<String?>(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllOtherExceptions(ex: Exception): ResponseEntity<String?> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body<String?>(ex.message)
    }
}
