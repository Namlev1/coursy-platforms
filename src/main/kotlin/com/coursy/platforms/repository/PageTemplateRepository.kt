package com.coursy.platforms.repository

import com.coursy.platforms.model.page.PageTemplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PageTemplateRepository : JpaRepository<PageTemplate, Long>, JpaSpecificationExecutor<PageTemplate> 