package com.coursy.platforms.failure

import com.coursy.platforms.types.Email
import java.util.*

sealed class PlatformFailure : Failure {
    data class NotFound(val id: UUID) : PlatformFailure()
    data class InvalidEmail(val email: Email, val id: UUID) : PlatformFailure()

    override fun message(): String = when (this) {
        is NotFound -> "Platform with id=${id} was not found"
        is InvalidEmail -> "User $email does not have access to platform with id=${id}"
    }
}