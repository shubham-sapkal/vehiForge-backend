package com.vehiforge.configurationService.core.health.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun default(): String {
        return "Configuration Service is running!"
    }

}