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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminPlatformControllerTest(
    private val mockMvc: MockMvc
) : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    val adminUrl = "/v1/admin/platform"

    init {
        describe("Authorization") {
            it("should not allow unauthorized access") {
                mockMvc.get(adminUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should not allow authorized user") {
                authorizeNextRequest(isAdmin = false)
                mockMvc.get(adminUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should allow authorized admin") {
                authorizeNextRequest(isAdmin = true)
                mockMvc.get(adminUrl)
                    .andExpect { status { isOk() } }

            }
        }

        describe("Get all platforms") {
            it("should pass") {
                authorizeNextRequest()
                mockMvc.get("$adminUrl") {
                    accept(MediaType.APPLICATION_JSON)
                }
                    .andExpect { status { isOk() } }
            }
        }
    }

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