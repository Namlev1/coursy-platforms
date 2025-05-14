package com.coursy.masterservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenFilter: JwtTokenFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/v1/auth/**").permitAll()
                    .requestMatchers("/v1/admin/**").hasAuthority(RoleName.ROLE_ADMIN.name)
                    .anyRequest().authenticated()
            }

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}