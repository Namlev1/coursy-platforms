package com.coursy.platforms.repository

import com.coursy.platforms.model.PlatformConfig
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ConfigRepository : JpaRepository<PlatformConfig, UUID>