package com.coursy.masterservice.dto

import arrow.core.Either
import arrow.core.raise.either
import com.coursy.masterservice.failure.Failure
import com.coursy.masterservice.failure.ValidationFailure
import com.coursy.masterservice.model.Platform
import com.coursy.masterservice.types.Description
import com.coursy.masterservice.types.Email
import com.coursy.masterservice.types.Name

data class PlatformRequest(
    val name: String,
    val description: String,
) : SelfValidating<Failure, PlatformRequest.Validated> {
    data class Validated(
        val name: Name,
        val description: Description
    ) {
        fun toModel(userEmail: Email) = Platform(
            id = null,
            userEmail = userEmail.value,
            name = this.name.value,
            description = this.description.value
        )
    }

    override fun validate(): Either<ValidationFailure, Validated> {
        return either {
            val validName = Name.create(name).bind()
            val validDescription = Description.create(description).bind()

            Validated(
                name = validName,
                description = validDescription,
            )
        }
    }
}