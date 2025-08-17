package com.vehiforge.userService.core.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableMethodSecurity
class AuthenticationConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http.build()

    }


}