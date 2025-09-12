package com.coursy.platforms.controller

import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.service.NavbarFooterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/platforms/{platformId}")
class NavbarFooterController(
    private val navbarFooterService: NavbarFooterService
) {

    @GetMapping("/navbar")
    fun getNavbarConfig(@PathVariable platformId: UUID): ResponseEntity<Any> =
        navbarFooterService
            .getNavbarConfig(platformId)
            .fold(
                { failure: PlatformFailure -> handleFailure(failure) },
                { navbarConfig -> ResponseEntity.ok(navbarConfig) }
            )

    @GetMapping("/footer")
    fun getFooterList(@PathVariable platformId: UUID) =
        navbarFooterService
            .getFooterConfig(platformId)
            .fold(
                { failure: PlatformFailure -> handleFailure(failure) },
                { footerList -> ResponseEntity.ok(footerList) }
            )

    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}