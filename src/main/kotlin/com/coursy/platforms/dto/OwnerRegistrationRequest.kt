package com.coursy.platforms.dto

import java.util.*

data class OwnerRegistrationRequest(
    val userId: UUID,
    val platformId: UUID
)
