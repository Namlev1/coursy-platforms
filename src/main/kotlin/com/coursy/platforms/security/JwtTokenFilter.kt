package com.coursy.platforms.security

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.failure.AuthHeaderFailure
import com.coursy.platforms.failure.JwtFailure
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            extractAndProcessJwt(request)
        } finally {
            filterChain.doFilter(request, response)
        }
    }

    private fun extractAndProcessJwt(request: HttpServletRequest) {
        parseJwt(request).fold(
            { authHeaderFailure -> handleHeaderFailure(authHeaderFailure) },
            { jwt -> authenticateWithJwt(jwt, request) }
        )
    }

    private fun authenticateWithJwt(jwt: String, request: HttpServletRequest) {
        setAuthenticationContext(jwt, request).fold(
            { jwtError -> logger.warn("JWT token processing error: ${jwtError.message()}") },
            { /* Authentication successful, no action needed */ }
        )
    }

    private fun setAuthenticationContext(
        jwt: String,
        request: HttpServletRequest
    ): Either<JwtFailure, Unit> {
        return try {
            val userDetails = jwtUtils.getUserDetailsFromToken(jwt)

            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            ).apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }

            SecurityContextHolder.getContext().authentication = authentication
            Unit.right()
        } catch (e: Exception) {
            JwtFailure.InvalidToken(jwt).left()
        }
    }

    private fun parseJwt(request: HttpServletRequest): Either<AuthHeaderFailure, String> {
        val headerAuth = request.getHeader("Authorization")
            ?: return AuthHeaderFailure.MissingHeader.left()

        return if (headerAuth.startsWith("Bearer ")) {
            headerAuth.removePrefix("Bearer ").trim().right()
        } else {
            AuthHeaderFailure.InvalidHeaderFormat(headerAuth).left()
        }
    }

    private fun handleHeaderFailure(error: AuthHeaderFailure) {
        when (error) {
            is AuthHeaderFailure.InvalidHeaderFormat ->
                logger.warn("Invalid Authorization header format: ${error.message()}")

            else -> {}
        }
    }
}