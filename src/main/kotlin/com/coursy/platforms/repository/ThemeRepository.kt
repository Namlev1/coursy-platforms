package com.coursy.platforms.repository

import com.coursy.platforms.model.Theme
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface ThemeRepository : JpaRepository<Theme, UUID>, JpaSpecificationExecutor<Theme> {
    fun findByPlatformId(platformId: UUID): Theme?
}