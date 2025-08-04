package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.dto.ThemeResponse
import com.coursy.platforms.dto.toResponse
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.ThemeFailure
import com.coursy.platforms.repository.ThemeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ThemeService(
    private val themeRepo: ThemeRepository
) {

    fun getByPlatformId(id: UUID): Either<Failure, ThemeResponse> {
        val theme = themeRepo.findByPlatformId(id)
            ?: return ThemeFailure.NotFound(id).left()
        return theme.toResponse().right()

    }
}