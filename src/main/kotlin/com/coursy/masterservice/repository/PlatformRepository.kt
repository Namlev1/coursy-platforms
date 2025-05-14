package com.coursy.masterservice.repository

import com.coursy.masterservice.model.Platform
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlatformRepository : JpaRepository<Platform, Long?> 