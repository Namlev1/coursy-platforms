package com.coursy.platforms.controller

import com.coursy.platforms.dto.PlatformRequest
import com.coursy.platforms.security.RoleName
import com.coursy.platforms.security.UserDetailsImp
import com.coursy.platforms.types.Description
import com.coursy.platforms.types.Email
import com.coursy.platforms.types.Name
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class ControllerTestFixtures {
    val adminUrl = "/api/admin/platform"
    val userUrl = "/api/user/platform"
    val testEmail = Email.create("test_email@email.com").getOrNull()!!
    val testName = Name.create("test name").getOrNull()!!
    val testDescription = Description.create("test description").getOrNull()!!
    val testPlatformRequest = PlatformRequest.Validated(testName, testDescription)

    fun authorizeNextRequest(isAdmin: Boolean = true) {
        val email = Email.create("aoe@aoeu.com").getOrNull()!!
        val roleName = if (isAdmin) RoleName.ROLE_ADMIN else RoleName.ROLE_USER
        val authorities = mutableListOf(SimpleGrantedAuthority(roleName.toString()))
        val userDetails = UserDetailsImp(email, authorities)

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
    }
}