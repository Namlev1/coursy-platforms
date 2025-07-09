package com.coursy.platforms.repository

import com.coursy.platforms.model.Platform
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlatformRepository : JpaRepository<Platform, UUID> {
    fun getByUserEmail(email: String): List<Platform>
}