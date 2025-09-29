package com.vehiforge.configurationService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


@SpringBootApplication
@EnableFeignClients(basePackages = ["com.vehiforge.configurationService.core.security.introspects"])
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class ConfigurationServiceApplication

fun main(args: Array<String>) {
	runApplication<ConfigurationServiceApplication>(*args)
}
