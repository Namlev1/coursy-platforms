package com.coursy.masterservice.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.coursy.masterservice.types.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

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

    fun getUserDetailsFromToken(token: String): UserDetailsImp {
        val verified = verifyToken(token)

        val email = Email(verified.subject)
        val rolesClaim = verified
            .getClaim("roles")
            .asList(SimpleGrantedAuthority::class.java) as MutableList<SimpleGrantedAuthority>
       
        return UserDetailsImp(email, rolesClaim)
    }
}