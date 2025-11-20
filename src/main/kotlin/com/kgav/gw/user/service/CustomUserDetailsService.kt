package com.kgav.gw.user.service

import com.kgav.gw.user.mapper.UserMapper
import com.kgav.gw.user.model.UserDetailsImpl
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService( private val userMapper: UserMapper ) : UserDetailsService {

    override fun loadUserByUsername(userid: String): UserDetails {
        println("loadUserByUsername 도달")
        val user = userMapper.findById(userid)
            ?: throw UsernameNotFoundException("ID 확인: $userid")
        println("user확인 : $user")

//        override fun getAuthorities(): Collection<GrantedAuthority> = user.commonName.map { SimpleGrantedAuthority(it) }

        return UserDetailsImpl(user)
        /*
        return object : UserDetails {
            override fun getAuthorities(): Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(user.commonName))
            override fun getPassword(): String = user.userpw
            override fun getUsername(): String = user.username
            override fun isAccountNonExpired(): Boolean = true
            override fun isAccountNonLocked(): Boolean = true
            override fun isCredentialsNonExpired(): Boolean = true
            override fun isEnabled(): Boolean = true
        }
        */
    }
}
