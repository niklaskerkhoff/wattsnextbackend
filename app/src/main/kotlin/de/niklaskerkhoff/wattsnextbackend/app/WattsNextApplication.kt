package de.niklaskerkhoff.wattsnextbackend.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WattsNextApplication

fun main(args: Array<String>) {
    runApplication<WattsNextApplication>(*args)
}
