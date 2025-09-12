package com.coursy.platforms.controller

import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.model.navbar.NavbarConfig
import com.coursy.platforms.service.ConfigService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

// TODO adjust security
@RestController
@RequestMapping("/api/platforms/{platformId}")
class ConfigController(
    private val configService: ConfigService
) {

    @GetMapping("/navbar")
    fun getNavbarConfig(@PathVariable platformId: UUID): ResponseEntity<Any> =
        configService
            .getNavbarConfig(platformId)
            .fold(
                { failure: PlatformFailure -> handleFailure(failure) },
                { navbarConfig: NavbarConfig -> ResponseEntity.ok(navbarConfig) }
            )

    @GetMapping("/footer")
    fun getFooterConfig() = ""

    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}