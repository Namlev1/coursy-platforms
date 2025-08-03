package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.dto.PageTemplateRequest
import com.coursy.platforms.dto.PageTemplateResponse
import com.coursy.platforms.dto.toResponse
import com.coursy.platforms.failure.AuthorizationFailure
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.repository.PageTemplateRepository
import com.coursy.platforms.repository.PlatformRepository
import com.coursy.platforms.security.RoleName
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class PageTemplateService(
    val templateRepo: PageTemplateRepository,
    val platformRepo: PlatformRepository
) {

    fun saveTemplate(
        platformId: UUID,
        dto: PageTemplateRequest.Validated,
        jwt: PreAuthenticatedAuthenticationToken
    ): Either<Failure, PageTemplateResponse> {
        val platform = platformRepo.findByIdOrNull(platformId) ?: return PlatformFailure.NotFound(platformId).left()
        if (!isOperationPermitted(jwt))
            return AuthorizationFailure.UnauthorizedAccess.left()

        val template = dto.toModel()
        platform.templates.add(template)
        platformRepo.save(platform)

        return template.toResponse().right()
    }

    private fun isOperationPermitted(jwt: PreAuthenticatedAuthenticationToken): Boolean {
        val authorities = jwt.authorities
        return authorities.any { authority ->
            authority.toString() == RoleName.ROLE_ADMIN.toString()
                    || authority.toString() == RoleName.ROLE_SUPER_ADMIN.toString()
        }
    }
}