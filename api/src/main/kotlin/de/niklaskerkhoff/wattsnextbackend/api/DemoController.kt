package de.niklaskerkhoff.wattsnextbackend.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {
    @GetMapping("greet")
    fun greet() = "Hello World!"
}
