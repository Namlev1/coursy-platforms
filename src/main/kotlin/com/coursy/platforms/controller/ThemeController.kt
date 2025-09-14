package com.coursy.platforms.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/platforms/{platformId}/theme")
class ThemeController(
) {

    // todo
//    @GetMapping
//    fun getTheme(@PathVariable platformId: UUID): ResponseEntity<Any> {
//        return themeService
//            .getByPlatformId(platformId)
//            .fold(
//                { ResponseEntity.status(HttpStatus.NOT_FOUND).body(it.message()) },
//                { ResponseEntity.ok(it) }
//            )
//
//    }
}
