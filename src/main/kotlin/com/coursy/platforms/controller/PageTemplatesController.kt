package com.coursy.platforms.controller

import arrow.core.flatMap
import com.coursy.platforms.dto.PageTemplateRequest
import com.coursy.platforms.service.PageTemplateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/platforms/{platformId}/templates")
class PageTemplatesController(
    val pageTemplateService: PageTemplateService
) {

    @PostMapping
    fun addNewTemplate(
        @PathVariable platformId: UUID,
        @RequestBody dto: PageTemplateRequest,
        jwt: PreAuthenticatedAuthenticationToken
    ): ResponseEntity<Any> {
        return dto
            .validate()
            .flatMap { validated ->
                pageTemplateService.saveTemplate(platformId, validated, jwt)
            }
            .fold(
                { ResponseEntity.status(HttpStatus.FORBIDDEN).body(it.message()) },
                { ResponseEntity.status(HttpStatus.CREATED).body(it) }
            )

    }
}