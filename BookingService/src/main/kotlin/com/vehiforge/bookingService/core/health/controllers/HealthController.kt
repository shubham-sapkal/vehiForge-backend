package com.vehiforge.bookingService.core.health.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun default(): String {
        return "Booking Service is up and running"
    }

    // Liveness API

    // Readiness API

}