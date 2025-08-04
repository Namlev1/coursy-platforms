package com.coursy.platforms.controller

import com.coursy.platforms.service.ThemeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/platforms/{platformId}/theme")
class ThemeController(
    private val themeService: ThemeService
) {

    @GetMapping
    fun getTheme(@PathVariable platformId: UUID): ResponseEntity<Any> {
        return themeService
            .getByPlatformId(platformId)
            .fold(
                { ResponseEntity.status(HttpStatus.NOT_FOUND).body(it.message()) },
                { ResponseEntity.ok(it) }
            )

    }
}
