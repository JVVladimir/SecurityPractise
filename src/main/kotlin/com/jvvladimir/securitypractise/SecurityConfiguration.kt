package com.jvvladimir.securitypractise

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Value("\${security.enable-csrf:true}")
    private val csrfEnabled = false

    override fun configure(http: HttpSecurity) {
        super.configure(http)

        if (!csrfEnabled) {
            http.csrf().disable()
            http.httpBasic().disable()
        }
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/css/get", "/hui")
    }
}