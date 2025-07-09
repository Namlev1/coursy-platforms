package com.coursy.platforms.controller

import arrow.core.flatMap
import com.coursy.platforms.dto.PlatformRequest
import com.coursy.platforms.dto.PlatformResponse
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.security.readToken
import com.coursy.platforms.service.PlatformService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user/platform")
class UserPlatformController(val service: PlatformService) {

    @GetMapping
    fun getAllPlatforms(jwt: PreAuthenticatedAuthenticationToken): List<PlatformResponse> {
        val (email, _) = jwt.readToken()
        return service.getByUserEmail(email)

    }


    @PostMapping
    fun createPlatform(
        @RequestBody request: PlatformRequest,
        jwt: PreAuthenticatedAuthenticationToken
    ): ResponseEntity<Any> {
        val (email, _) = jwt.readToken()

        return request
            .validate()
            .flatMap { service.savePlatform(it, email) }
            .fold(
                { handleFailure(it) },
                { ResponseEntity.status(HttpStatus.CREATED).build() }
            )
    }

    @DeleteMapping("/{id}")
    fun deletePlatform(
        @PathVariable id: Long,
        jwt: PreAuthenticatedAuthenticationToken
    ) {
        val (email, _) = jwt.readToken()

        service.deletePlatform(id, email)
            .fold(
                { handleFailure(it) },
                { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
            )
    }

    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}