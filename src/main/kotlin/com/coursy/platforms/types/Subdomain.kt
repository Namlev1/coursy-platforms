package com.coursy.platforms.types

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.failure.ValidationFailure

@JvmInline
value class Subdomain private constructor(val value: String) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 63

        fun create(value: String): Either<ValidationFailure, Subdomain> = when {
            value.isEmpty() -> ValidationFailure.Empty.left()
            value.length < MIN_LENGTH -> ValidationFailure.TooShort(MIN_LENGTH).left()
            value.length > MAX_LENGTH -> ValidationFailure.TooLong(MAX_LENGTH).left()
            else -> Subdomain(value).right()
        }
    }
}