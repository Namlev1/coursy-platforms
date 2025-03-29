package com.coursy.be.controller

import com.coursy.be.model.Platform
import com.coursy.be.service.PlatformService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/platform")
class PlatformController(val service: PlatformService) {

    @GetMapping
    fun getAllPlatforms() = service.getAllPlatforms()

    @PostMapping
    fun createPlatform(@RequestBody platform: Platform) = service.savePlatform(platform)

    @DeleteMapping("/{id}")
    fun deletePlatform(@PathVariable id: Long) = service.deletePlatform(id)
}