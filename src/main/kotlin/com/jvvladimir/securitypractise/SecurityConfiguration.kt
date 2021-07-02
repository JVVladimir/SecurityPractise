package com.jvvladimir.securitypractise

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Value("\${security.enable-csrf:true}")
    private val csrfEnabled = false

    override fun configure(http: HttpSecurity) {
        super.configure(http)

        if (!csrfEnabled) {
            http.csrf().disable()
        }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(inMemoryUserDetailsManager())
        auth.authenticationProvider(authenticationProvider())
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/css/get")
    }

    @Bean
    fun authenticationProvider (): AuthenticationProvider {
        return MyAuthenticationProvider()
    }

    @Bean
    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
        return InMemoryUserDetailsManager(
            User(
                "vova",
                passwordEncoder().encode("pass"),
                listOf(SimpleGrantedAuthority("ADMIN"))
            )
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

class MyAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        println("Auth all!")
        val username = authentication.principal.toString()
        val password = authentication.credentials.toString()

        return UsernamePasswordAuthenticationToken(username, password, authentication.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

}
