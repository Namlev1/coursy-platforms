package com.coursy.masterservice.controller

import com.coursy.masterservice.dto.PlatformRequest
import com.coursy.masterservice.security.RoleName
import com.coursy.masterservice.security.UserDetailsImp
import com.coursy.masterservice.types.Description
import com.coursy.masterservice.types.Email
import com.coursy.masterservice.types.Name
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class ControllerTestFixtures {
    val adminUrl = "/v1/admin/platform"
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