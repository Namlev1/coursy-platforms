package com.coursy.be.repository

import com.coursy.be.model.platform.Platform
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlatformRepository : JpaRepository<Platform, Long?> 