package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.dto.PageTemplateRequest
import com.coursy.platforms.dto.PageTemplateResponse
import com.coursy.platforms.dto.toResponse
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PageTemplateFailure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.model.page.PageType
import com.coursy.platforms.repository.PageTemplateRepository
import com.coursy.platforms.repository.PlatformRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class PageTemplateService(
    val templateRepo: PageTemplateRepository,
    val platformRepo: PlatformRepository
) {
    fun find(
        platformId: UUID,
        type: PageType,
    ): Either<Failure, PageTemplateResponse> {
        val platform = platformRepo.findByIdOrNull(platformId) ?: return PlatformFailure.NotFound(platformId).left()
        val template = platform.templates.find { it.type == type }
            ?: return PageTemplateFailure.InvalidType(type.toString()).left()

        return template.toResponse().right()
    }

    fun saveTemplate(
        platformId: UUID,
        dto: PageTemplateRequest.Validated,
    ): Either<Failure, PageTemplateResponse> {
        val platform = platformRepo.findByIdOrNull(platformId) ?: return PlatformFailure.NotFound(platformId).left()

        val template = dto.toModel()
        platform.templates.add(template)
        platformRepo.save(platform)

        return template.toResponse().right()
    }
}