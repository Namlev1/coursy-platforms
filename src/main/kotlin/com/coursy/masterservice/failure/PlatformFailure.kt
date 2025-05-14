package com.coursy.masterservice.failure

sealed class PlatformFailure : Failure {
    data class NotFound(val id: Long) : PlatformFailure()

    override fun message(): String = when (this) {
        is NotFound -> "Platform with id=${id} was not found"
    }
}