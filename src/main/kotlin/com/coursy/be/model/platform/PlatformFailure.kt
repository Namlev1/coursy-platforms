package com.coursy.be.model.platform

sealed class PlatformFailure {
    data class NotFound(val id: Long) : PlatformFailure()
}