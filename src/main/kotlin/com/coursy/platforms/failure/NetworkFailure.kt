package com.coursy.platforms.failure

class NetworkFailure(
    val message: String
) : Failure {
    override fun message(): String = message
}
