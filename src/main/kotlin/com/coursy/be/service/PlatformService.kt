package com.coursy.be.service

import com.coursy.be.model.Platform
import com.coursy.be.repository.PlatformRepository
import org.springframework.stereotype.Service

@Service
class PlatformService(val repo: PlatformRepository) {
    fun getAllPlatforms(): Iterable<Platform> = repo.findAll()
    fun savePlatform(platform: Platform) = repo.save(platform)
    fun deletePlatform(id: Long) = repo.deleteById(id)
}