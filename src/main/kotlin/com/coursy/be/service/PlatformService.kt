package com.coursy.be.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.be.model.platform.Platform
import com.coursy.be.model.platform.PlatformFailure
import com.coursy.be.repository.PlatformRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlatformService(val repo: PlatformRepository) {
    fun getAllPlatforms(): Iterable<Platform> = repo.findAll()
    fun savePlatform(platform: Platform) = repo.save(platform)
    fun deletePlatform(id: Long) = repo.deleteById(id)

    fun getById(id: Long): Either<PlatformFailure, Platform> {
        return repo.findByIdOrNull(id)?.right() ?: PlatformFailure.NotFound(id).left()
    }
}