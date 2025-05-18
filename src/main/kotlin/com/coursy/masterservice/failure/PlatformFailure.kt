package com.coursy.masterservice.failure

import com.coursy.masterservice.types.Email

sealed class PlatformFailure : Failure {
    data class NotFound(val id: Long) : PlatformFailure()
    data class InvalidEmail(val email: Email, val id: Long) : PlatformFailure()

    override fun message(): String = when (this) {
        is NotFound -> "Platform with id=${id} was not found"
        is InvalidEmail -> "User $email does not have access to platform with id=${id}"
    }
}