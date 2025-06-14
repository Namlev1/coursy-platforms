package com.coursy.platforms.dto

data class JwtResponse(
    val token: String,
    val refreshToken: String,
)
