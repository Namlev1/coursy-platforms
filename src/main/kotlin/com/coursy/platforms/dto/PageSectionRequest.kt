package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.right
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.ValidationFailure
import com.coursy.platforms.model.PageSection
import com.fasterxml.jackson.databind.JsonNode

data class PageSectionRequest(
    val type: String,
    val order: Int,
    val props: JsonNode
) : SelfValidating<Failure, PageSectionRequest.Validated> {
    data class Validated(
        val type: String,
        val order: Int,
        val props: JsonNode
    ) {
        fun toModel() = PageSection(
            type = type,
            order = order,
            props = props
        )
    }

    override fun validate(): Either<ValidationFailure, Validated> {
        // TODO actually validation
        return Validated(
            type = type,
            order = order,
            props = props
        ).right()
    }
}
