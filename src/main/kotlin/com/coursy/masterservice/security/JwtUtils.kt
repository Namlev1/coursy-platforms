package com.coursy.masterservice.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.coursy.masterservice.types.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Collections.emptyList

@Component
class JwtUtils(
    @Value("\${jwt.secret}")
    private val jwtSecret: String
) {
    private fun verifyToken(token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
            .build()
            .verify(token)
    }

    // todo merge these 2 methods
    fun getEmailFromToken(token: String): Email {
        val email = verifyToken(token)
            .subject
        return Email(email)

    }

    fun getRolesFromToken(token: String): MutableList<SimpleGrantedAuthority> {
        val jwt = verifyToken(token)

        val rolesClaim = jwt.getClaim("roles")


        if (rolesClaim.isNull) {
            return emptyList()
        }

        return rolesClaim.asList(String::class.java)
            .map { role -> SimpleGrantedAuthority(role) } as MutableList<SimpleGrantedAuthority>
    }
}