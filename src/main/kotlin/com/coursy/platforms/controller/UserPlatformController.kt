package com.coursy.platforms.controller

import arrow.core.flatMap
import com.coursy.platforms.dto.PlatformRequest
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.security.UserDetailsImp
import com.coursy.platforms.service.PlatformService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/platform")
class UserPlatformController(val service: PlatformService) {

    @GetMapping
    fun getAllPlatforms(@AuthenticationPrincipal user: UserDetailsImp) =
        service.getByUserEmail(user.email)


    @PostMapping
    fun createPlatform(
        @RequestBody request: PlatformRequest,
        @AuthenticationPrincipal user: UserDetailsImp
    ): ResponseEntity<Any> = request
        .validate()
        .flatMap { service.savePlatform(it, user.email) }
        .fold(
            { handleFailure(it) },
            { ResponseEntity.status(HttpStatus.CREATED).build() }
        )

    @DeleteMapping("/{id}")
    fun deletePlatform(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: UserDetailsImp
    ) {
        service.deletePlatform(id, user.email)
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