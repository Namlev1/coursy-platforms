package com.coursy.masterservice.controller

import com.coursy.masterservice.failure.Failure
import com.coursy.masterservice.failure.PlatformFailure
import com.coursy.masterservice.service.PlatformService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @DeleteMapping("/{id}")
    fun deletePlatform(@PathVariable id: Long): ResponseEntity<Any> {
        service.deletePlatform(id)
        return ResponseEntity.noContent().build()
    } 

    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}