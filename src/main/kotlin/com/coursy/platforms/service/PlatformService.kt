package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.dto.PlatformRequest
import com.coursy.platforms.dto.PlatformResponse
import com.coursy.platforms.dto.toResponse
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.model.Platform
import com.coursy.platforms.repository.PlatformRepository
import com.coursy.platforms.types.Email
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlatformService(val repo: PlatformRepository) {
    fun getAllPlatforms(): List<PlatformResponse> =
        repo
            .findAll()
            .map(Platform::toResponse)

    fun savePlatform(
        dto: PlatformRequest.Validated,
        email: Email
    ) = repo
        .save(dto.toModel(email))
        .right()

    fun deletePlatform(id: Long) = repo.deleteById(id)

    fun deletePlatform(platformId: Long, userEmail: Email): Either<PlatformFailure, Unit> {
        val platform = repo.findByIdOrNull(platformId) ?: return PlatformFailure.NotFound(platformId).left()
        if (platform.userEmail != userEmail.value)
            return PlatformFailure.InvalidEmail(userEmail, platformId).left()

        repo.deleteById(platformId)
        return Unit.right()
    } 

    fun getById(id: Long) =
        repo
            .findByIdOrNull(id)?.toResponse()?.right()
            ?: PlatformFailure.NotFound(id).left()

    fun getByUserEmail(email: Email) =
        repo.getByUserEmail(email.value)
            .map(Platform::toResponse)
}