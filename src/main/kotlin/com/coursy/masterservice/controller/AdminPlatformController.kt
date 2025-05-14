package com.coursy.masterservice.controller

import com.coursy.masterservice.dto.PlatformDto
import com.coursy.masterservice.failure.PlatformFailure
import com.coursy.masterservice.security.UserDetailsImp
import com.coursy.masterservice.service.PlatformService
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
        @RequestBody dto: PlatformDto,
        @AuthenticationPrincipal user: UserDetailsImp
    ) = service.savePlatform(dto, user.email)

    @DeleteMapping("/{id}")
    fun deletePlatform(@PathVariable id: Long) = service.deletePlatform(id)

    private fun handleFailure(failure: PlatformFailure) =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.notFound().build<Any>()
        }
}