package com.coursy.masterservice.dto

import arrow.core.Either

interface SelfValidating<A, B> {
    fun validate(): Either<A, B>
}
