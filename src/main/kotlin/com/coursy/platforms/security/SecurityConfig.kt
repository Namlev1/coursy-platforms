package com.coursy.platforms.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/platforms/{platformId}/templates/{type}",
                        "/api/platforms/{platformId}/theme"
                    )
                    .permitAll()

                    .requestMatchers("/api/platforms/**")
                    .hasAnyAuthority(
                        Role.ROLE_HOST_OWNER.toString(),
                        Role.ROLE_HOST_ADMIN.toString(),
                        Role.ROLE_TENANT.toString(),
                    )

                    .requestMatchers("/api/admin/platforms/**")
                    .hasAnyAuthority(
                        Role.ROLE_HOST_OWNER.toString(),
                        Role.ROLE_HOST_ADMIN.toString(),
                    )

                    .anyRequest()
                    .authenticated()
            }

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}