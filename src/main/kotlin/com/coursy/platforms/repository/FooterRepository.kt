package com.coursy.platforms.repository

import com.coursy.platforms.model.footer.FooterItem
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FooterRepository : JpaRepository<FooterItem, UUID> {
    fun findAllByPlatform_Id(platformId: UUID): List<FooterItem>
}
