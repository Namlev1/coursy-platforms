package com.coursy.platforms.failure

import java.util.*

sealed class PageTemplateFailure(private val identifier: String) : Failure {
    class NotFoundId(val id: UUID) : PageTemplateFailure("id=${id}")
    class NotFoundTitle(val title: String) : PageTemplateFailure("title=${title}")
    class InvalidType(val type: String) : PageTemplateFailure("type=${type}")

    override fun message(): String = "Template with $identifier was not found"
}