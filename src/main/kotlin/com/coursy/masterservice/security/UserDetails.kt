package com.coursy.masterservice.security

import com.coursy.masterservice.types.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImp(
    val email: Email,
    private val authorities: MutableCollection<SimpleGrantedAuthority>,
    private val enabled: Boolean = true,
    private val accountNonLocked: Boolean = true,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword() = ""

    override fun getUsername() = email.value

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = accountNonLocked

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled

}
