package com.coursy.platforms.repository

import com.coursy.platforms.model.navbar.NavbarConfig
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NavbarRepository : JpaRepository<NavbarConfig, UUID> 