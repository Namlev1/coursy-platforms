//package com.coursy.platforms.controller
//
//import com.auth0.jwt.JWT
//import com.auth0.jwt.algorithms.Algorithm
//import com.coursy.platforms.dto.PlatformRequest
//import com.coursy.platforms.security.RoleName
//import com.coursy.platforms.types.Description
//import com.coursy.platforms.types.Email
//import com.coursy.platforms.types.Name
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
//import java.util.*
//
//class ControllerTestFixtures {
//    val adminUrl = "/api/admin/platform"
//    val userUrl = "/api/user/platform"
//    val testEmail = Email.create("test_email@email.com").getOrNull()!!
//    val testName = Name.create("test name").getOrNull()!!
//    val testDescription = Description.create("test description").getOrNull()!!
//    val testPlatformRequest = PlatformRequest.Validated(testName, testDescription)
//
//    // todo get rid of this method
//    fun authorizeNextRequest(isAdmin: Boolean = true) {
//        val email = Email.create("aoe@aoeu.com").getOrNull()!!
//        val roleName = if (isAdmin) RoleName.ROLE_ADMIN else RoleName.ROLE_USER
//        val authorities = mutableListOf(SimpleGrantedAuthority(roleName.toString()))
//
//        val token = JWT.create()
//            .withSubject(email.value)
//            .withClaim("roles", listOf(roleName.toString()))
//            .withClaim("id", UUID.randomUUID().toString())
//            .sign(Algorithm.none())
//
//        val decodedToken = JWT.decode(token)
//
//        val authentication = PreAuthenticatedAuthenticationToken(
//            email, decodedToken, authorities
//        )
//
//        SecurityContextHolder.getContext().authentication = authentication
//    }
//}