package com.vehiforge.bookingService.core.security.filters

import com.vehiforge.bookingService.core.security.introspects.IntrospectClient
import com.vehiforge.bookingService.core.security.introspects.TokenValidationReq
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.text.startsWith
import kotlin.text.substring

@Component
class JwtAuthFilter(
    private val introspectClient: IntrospectClient,
    @Value("\${security.user-service-url}")
    val url: String
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        println("In Filter $url")

        val authHeader = request.getHeader("Authorization")

        if(authHeader == null || !authHeader.startsWith("Bearer ") ) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)

        val introspectResponse =
            introspectClient.introspect(TokenValidationReq(token))


        println("In Filter $introspectResponse")

        if( introspectResponse?.status == 200 && introspectResponse.result?.isValid == true) {

            val auth = UsernamePasswordAuthenticationToken(
                introspectResponse.result.username, null, introspectResponse.result.roles.map { SimpleGrantedAuthority(it) }
            )
            auth.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = auth

        }

        filterChain.doFilter(request, response)

    }

}