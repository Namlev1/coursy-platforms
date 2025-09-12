package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.dto.footer.FooterItemResponse
import com.coursy.platforms.dto.footer.toResponse
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.model.navbar.NavbarConfig
import com.coursy.platforms.repository.FooterRepository
import com.coursy.platforms.repository.NavbarRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional
class NavbarFooterService(
    private val navRepository: NavbarRepository,
    private val footerRepository: FooterRepository
) {
    fun getNavbarConfig(platformId: UUID): Either<PlatformFailure, NavbarConfig> =
        navRepository
            .findById(platformId)
            .getOrElse { return PlatformFailure.NotFound(platformId).left() }
            .right()

    fun getFooterConfig(platformId: UUID): Either<PlatformFailure, List<FooterItemResponse>> =
        footerRepository
            .findAllByPlatform_Id(platformId)
            .map { it.toResponse() }
            .right()
}