package com.coursy.platforms.controller

import com.coursy.platforms.service.ConfigService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// TODO adjust security
@RestController
@RequestMapping("/api/platforms/{platformId}")
class ConfigController(
    private val configService: ConfigService
) {

    @GetMapping("/navbar")
    fun getNavbarConfig() = ""

    @GetMapping("/footer")
    fun getFooterConfig() = ""
}