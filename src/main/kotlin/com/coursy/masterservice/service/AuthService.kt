package com.coursy.masterservice.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.masterservice.dto.JwtResponse
import com.coursy.masterservice.dto.LoginRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Service
class AuthService(
    @Value("\${auth.service.url}")
    private val authServiceUrl: String

) {
    private val webClient = WebClient.builder()
        .baseUrl(authServiceUrl)
        .build()

    fun loginUser(request: LoginRequest): Mono<Either<String, JwtResponse>> =
        webClient.post()
            .uri("/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(JwtResponse::class.java)
            .map { it.right() as Either<String, JwtResponse> }
            .onErrorResume {
                val errorMessage = when (it) {
                    is WebClientResponseException.Forbidden -> it.responseBodyAsString
                    else -> "Login failed: ${it.message}"
                }
                Mono.just(errorMessage.left())
            }
}