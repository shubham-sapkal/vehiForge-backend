package com.vehiforge.userService.core.security.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration-ms}") private val expirationMs: Long
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    /*
    * Description: Fun to Generate Token
    * */
    fun generateToken(user: UserDetails): String {

        val roles = user.authorities.map { it.authority }

        val now = Date()
        val expiry = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(user.username)
            .claim("roles", roles)
            .claim("user-info", "Implementation Pending")
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key)
            .compact()
    }

    /*
    * Description:
    * */
    fun parseClaims(token: String) =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)



    /*
    * Description:
    * */
    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    /*
    * Description:
    * */
    fun extractRoles(token: String): List<String> {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        @Suppress("UNCHECKED_CAST")
        return claims["roles"] as List<String>
    }


    /*
    * Description:
    * */
    fun isTokenValid(token: String, user: UserDetails): Boolean {
        return try {
            val body = parseClaims(token).body
            user.username == body.subject && body.expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }



}