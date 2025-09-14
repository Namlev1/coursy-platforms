package com.coursy.platforms.controller

import arrow.core.flatMap
import com.coursy.platforms.dto.PageTemplateRequest
import com.coursy.platforms.model.page.PageType
import com.coursy.platforms.service.PageTemplateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/platforms/{platformId}/templates")
class PageTemplatesController(
    val pageTemplateService: PageTemplateService
) {

    @GetMapping("/{type}")
    fun getByType(
        @PathVariable platformId: UUID,
        @PathVariable type: PageType
    ): ResponseEntity<Any> {
        return pageTemplateService
            .find(platformId, type)
            .fold(
                { ResponseEntity.status(HttpStatus.NOT_FOUND).body(it.message()) },
                { ResponseEntity.ok(it) }
            )
    }


    @PostMapping
    fun addNewTemplate(
        @PathVariable platformId: UUID,
        @RequestBody dto: PageTemplateRequest,
    ): ResponseEntity<Any> {
        return dto
            .validate()
            .flatMap { validated ->
                pageTemplateService.saveTemplate(platformId, validated)
            }
            .fold(
                { ResponseEntity.status(HttpStatus.FORBIDDEN).body(it.message()) },
                { ResponseEntity.status(HttpStatus.CREATED).body(it) }
            )

    }
}