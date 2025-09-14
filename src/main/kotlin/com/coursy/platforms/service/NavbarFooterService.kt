package com.coursy.platforms.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class NavbarFooterService(
//    private val navRepository: NavbarRepository,
//    private val footerRepository: FooterRepository
) {
    // todo
    /*    fun getNavbarConfig(platformId: UUID): Either<PlatformFailure, NavbarConfig> =
            navRepository
                .findById(platformId)
                .getOrElse { return PlatformFailure.NotFound(platformId).left() }
                .right()
    
        fun getFooterConfig(platformId: UUID): Either<PlatformFailure, List<FooterItemResponse>> =
            footerRepository
                .findAllByPlatform_Id(platformId)
                .map { it.toResponse() }
                .right()*/
}