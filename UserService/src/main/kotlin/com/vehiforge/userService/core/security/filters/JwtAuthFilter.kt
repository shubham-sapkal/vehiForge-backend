package com.vehiforge.userService.core.security.filters

import com.vehiforge.userService.core.security.services.CustomUserDetailsService
import com.vehiforge.userService.core.security.services.JwtService
import com.vehiforge.userService.core.users.services.UserServices
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        if(authHeader == null || !authHeader.startsWith("Bearer ") ) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)
        val username = try {
            jwtService.extractUsername(token)
        } catch (_: Exception ) { null }

        if(username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if ( jwtService.isTokenValid(token, userDetails) ) {
                val auth = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = auth
            }
        }

        filterChain.doFilter(request, response)

    }

}