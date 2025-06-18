package de.niklaskerkhoff.wattsnextbackend.api

import de.niklaskerkhoff.wattsnextbackend.model.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {
    @GetMapping("points")
    fun points() = Model().getPoints()
}
