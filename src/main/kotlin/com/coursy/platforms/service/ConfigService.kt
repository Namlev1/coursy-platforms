package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.model.PlatformConfig
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
        return configRepository.findById(platformId)
            .getOrElse { return PlatformFailure.NotFound(platformId).left() }
            .right()
    }
}