package com.vehiforge.bookingService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(basePackages = ["com.vehiforge.bookingService.core.security.introspects"])
class BookingServiceApplication

fun main(args: Array<String>) {
	runApplication<BookingServiceApplication>(*args)
}
