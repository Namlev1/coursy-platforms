package com.coursy.masterservice.types

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.masterservice.failure.EmailFailure

@JvmInline
value class Email(val value: String) {
    companion object {
        private val EMAIL_REGEX = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        private const val MIN_LENGTH = 6
        private const val MAX_LENGTH = 60

        fun validate(value: String): Either<EmailFailure, Email> = when {
            value.isEmpty() -> EmailFailure.Empty.left()
            "@" !in value -> EmailFailure.MissingAtSymbol.left()
            value.length < MIN_LENGTH -> EmailFailure.TooShort(MIN_LENGTH).left()
            value.length > MAX_LENGTH -> EmailFailure.TooLong(MIN_LENGTH).left()
            !value.matches(EMAIL_REGEX) -> EmailFailure.InvalidFormat.left()
            else -> Email(value).right()
        }
    }
}
