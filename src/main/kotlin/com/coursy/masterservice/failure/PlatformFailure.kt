package com.coursy.masterservice.failure

sealed class PlatformFailure {
    data class NotFound(val id: Long) : PlatformFailure()
}