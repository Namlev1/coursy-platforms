package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.dto.ConfigDto
import com.coursy.platforms.dto.footer.FooterItemDto
import com.coursy.platforms.dto.navbar.NavbarConfigDto
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.model.PlatformConfig
import com.coursy.platforms.model.navbar.NavbarConfig
import com.coursy.platforms.repository.ConfigRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional
class ConfigService(
    private val configRepository: ConfigRepository,
) {
    fun getByPlatformId(platformId: UUID): Either<PlatformFailure.NotFound, PlatformConfig> {
        return configRepository.findByPlatformId(platformId)
            .getOrElse { return PlatformFailure.NotFound(platformId).left() }
            .right()
    }

    // TODO authorization
    fun updateConfig(platformId: UUID, configDto: ConfigDto.Validated): Either<PlatformFailure, Unit> {
        val existingConfig = configRepository.getWithPlatformByPlatformId(platformId)
            ?: return PlatformFailure.NotFound(platformId).left()

        updateNavbarConfig(existingConfig.navbarConfig, configDto.navbarConfig)
        updateFooterItems(existingConfig, configDto.footerItems)
        updatePlatformProperties(existingConfig, configDto)

        configRepository.save(existingConfig)
        return Unit.right()
    }

    private fun updateNavbarConfig(navbarConfig: NavbarConfig, navbarDto: NavbarConfigDto) {
        navbarConfig.apply {
            navItems.clear()
            navItems.addAll(navbarDto.navItems.map { it.toModel() })
            logoUrl = navbarDto.logoUrl
            logoText = navbarDto.logoText
            isLogoVisible = navbarDto.isLogoVisible
        }
    }

    private fun updateFooterItems(config: PlatformConfig, footerItemDtos: List<FooterItemDto>) {
        config.footerItems.clear()
        config.footerItems.addAll(footerItemDtos.map { it.toModel() })
    }

    private fun updatePlatformProperties(config: PlatformConfig, configDto: ConfigDto.Validated) {
        config.apply {
            theme.colors = configDto.colors
            courseListLayout = configDto.courseListLayout
            videoPlayerType = configDto.videoPlayerType
        }
    }
}