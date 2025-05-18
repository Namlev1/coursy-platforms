package com.coursy.masterservice.controller

import com.coursy.masterservice.security.RoleName
import com.coursy.masterservice.security.UserDetailsImp
import com.coursy.masterservice.types.Email
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class AdminPlatformControllerTest(
    private val mockMvc: MockMvc
) : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    init {
        it("should pass") {
            login()
            mockMvc.get("/v1/admin/platform/lol") {
                accept(MediaType.APPLICATION_JSON)
            }
                .andExpect { status { isOk() } }
        }
    }

    fun login() {
        val email = Email.create("aoe@aoeu.com").getOrNull()!!
        val authorities = mutableListOf(SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
        val userDetails = UserDetailsImp(email, authorities)

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
    }
}