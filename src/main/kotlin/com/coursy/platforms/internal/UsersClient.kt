package com.coursy.platforms.internal

import arrow.core.Either
import com.coursy.platforms.dto.OwnerRegistrationRequest
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.NetworkFailure
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.util.*

@Service
class UsersClient(
    @Value("\${users.service.url}")
    private val usersServiceUrl: String,
    private val webClientBuilder: WebClient.Builder
) {
    private val webClient: WebClient = webClientBuilder
        .baseUrl(usersServiceUrl)
        .build()

    fun createOwner(userId: UUID, platformId: UUID): Either<Failure, Unit> {
        return try {
            webClient
                .post()
                .uri("${usersServiceUrl}/api/internal/users/owner")
                .header("Content-Type", "application/json")
                .bodyValue(
                    OwnerRegistrationRequest(
                        userId,
                        platformId,
                    )
                )
                .retrieve()
                .onStatus(HttpStatusCode::isError) { response ->
                    Mono.error(RuntimeException("Auth service error: ${response.statusCode()}"))
                }
                .bodyToMono<Unit>()
                .block()

            Either.Right(Unit)
        } catch (ex: Exception) {
            Either.Left(NetworkFailure(ex.message ?: "Unknown error"))
        }
    }
}