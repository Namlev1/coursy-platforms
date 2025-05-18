package com.coursy.masterservice.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.masterservice.dto.PlatformRequest
import com.coursy.masterservice.dto.PlatformResponse
import com.coursy.masterservice.dto.toResponse
import com.coursy.masterservice.failure.PlatformFailure
import com.coursy.masterservice.model.Platform
import com.coursy.masterservice.repository.PlatformRepository
import com.coursy.masterservice.types.Email
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