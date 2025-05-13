package com.coursy.masterservice.dto

data class JwtResponse(
    val token: String,
    val refreshToken: String,
)
