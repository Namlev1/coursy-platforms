package com.coursy.platforms.controller

import arrow.core.getOrElse
import com.coursy.platforms.dto.ConfigDto
import com.coursy.platforms.dto.toResponse
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.service.ConfigService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/platforms/{platformId}/config")
class ConfigController(
    private val configService: ConfigService,
) {
    @GetMapping
    fun getConfig(@PathVariable platformId: UUID): ResponseEntity<Any> {
        return configService
            .getByPlatformId(platformId)
            .fold(
                { failure -> handleFailure(failure) },
                { ResponseEntity.status(HttpStatus.OK).body(it.toResponse()) }
            )
    }

    @PutMapping
    fun updateConfig(@PathVariable platformId: UUID, dto: ConfigDto): ResponseEntity<Any> {
        val validated = dto
            .validate()
            .getOrElse { return ResponseEntity(HttpStatus.BAD_REQUEST) }
       
        return configService
            .updateConfig(platformId, validated)
            .fold(
                { failure -> handleFailure(failure) },
                { ResponseEntity.status(HttpStatus.OK).body(it) }
            )
    }
    
    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}