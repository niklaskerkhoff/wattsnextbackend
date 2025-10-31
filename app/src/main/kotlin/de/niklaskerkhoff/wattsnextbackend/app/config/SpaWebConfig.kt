package de.niklaskerkhoff.wattsnextbackend.app.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SpaWebConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/")
            .setViewName("forward:/index.html")

        // alles außer /api oder /ws und ohne Punkt (Dateiendung)
        registry.addViewController("/{path:^(?!api|ws$)[^\\.]*}")
            .setViewName("forward:/index.html")
    }
}

