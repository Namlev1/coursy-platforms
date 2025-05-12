package com.coursy.masterservice.model.platform

sealed class PlatformFailure {
    data class NotFound(val id: Long) : PlatformFailure()
}