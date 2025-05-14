package com.coursy.masterservice.service

import arrow.core.left
import arrow.core.right
import com.coursy.masterservice.dto.PlatformDto
import com.coursy.masterservice.dto.toDto
import com.coursy.masterservice.dto.toModel
import com.coursy.masterservice.failure.PlatformFailure
import com.coursy.masterservice.model.Platform
import com.coursy.masterservice.repository.PlatformRepository
import com.coursy.masterservice.types.Email
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlatformService(val repo: PlatformRepository) {
    fun getAllPlatforms(): List<PlatformDto> =
        repo.findAll().map(Platform::toDto)

    fun savePlatform(platform: PlatformDto, email: Email) = repo.save(platform.toModel(email))
    
    fun deletePlatform(id: Long) = repo.deleteById(id)

    fun getById(id: Long) =
        repo.findByIdOrNull(id)?.toDto()?.right() ?: PlatformFailure.NotFound(id).left()

    fun getByUserEmail(email: Email) =
        repo.getByUserEmail(email.value)
            .map(Platform::toDto)
}