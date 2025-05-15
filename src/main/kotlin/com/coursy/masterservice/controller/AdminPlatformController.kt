package com.coursy.masterservice.controller

import arrow.core.flatMap
import com.coursy.masterservice.dto.PlatformRequest
import com.coursy.masterservice.failure.Failure
import com.coursy.masterservice.failure.PlatformFailure
import com.coursy.masterservice.security.UserDetailsImp
import com.coursy.masterservice.service.PlatformService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/platform")
class AdminPlatformController(
    val service: PlatformService
) {
    @GetMapping
    fun getAllPlatforms() = service.getAllPlatforms()

    @GetMapping("/{id}")
    fun getPlatformById(@PathVariable id: Long) = service.getById(id).fold(
        { failure -> handleFailure(failure) },
        { platformDto -> ResponseEntity.ok(platformDto) }
    )

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
    fun deletePlatform(@PathVariable id: Long) = service.deletePlatform(id)

    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}