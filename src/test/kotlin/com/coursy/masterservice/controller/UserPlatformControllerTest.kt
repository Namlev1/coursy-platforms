package com.coursy.masterservice.controller

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import jakarta.transaction.Transactional
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserPlatformControllerTest(
    private val mockMvc: MockMvc
) : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)
    val fixtures = ControllerTestFixtures()

    val userUrl = fixtures.userUrl

    init {
        describe("Authorization") {
            it("should not allow unauthorized access") {
                mockMvc.get(userUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should allow authorized user") {
                fixtures.authorizeNextRequest(isAdmin = false)
                mockMvc.get(userUrl)
                    .andExpect { status { isOk() } }
            }

            it("should allow authorized admin") {
                fixtures.authorizeNextRequest(isAdmin = true)
                mockMvc.get(userUrl)
                    .andExpect { status { isOk() } }

            }
        }

    }
}
