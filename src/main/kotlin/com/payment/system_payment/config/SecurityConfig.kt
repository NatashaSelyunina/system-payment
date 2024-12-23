package com.payment.system_payment.config

import org.springframework.cglib.core.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val admin = InMemoryUserDetailsManager()
        admin.createUser(User.builder().username("admin").password("password").roles("ADMIN").build())
        return admin
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val allowedPaths = arrayOf("/booking", "/booking/status/{token}", "/booking/cancellation/{token}", "/payment", "/payment/check-status")
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers(*allowedPaths).permitAll()
                    .anyRequest().hasRole("ADMIN")
            }
            .httpBasic { }
        return http.build()
    }
}
