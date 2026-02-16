package de.niklaskerkhoff.wattsnextbackend.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WattsNextApplication

fun main(args: Array<String>) {
    runApplication<WattsNextApplication>(*args)
}
