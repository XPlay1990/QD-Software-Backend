package com.nicando.ediportal.security.config

import com.nicando.ediportal.user.admin.SwitchUserService
import com.nicando.ediportal.security.CustomUserDetailsService
import com.nicando.ediportal.security.JwtAuthenticationEntryPoint
import com.nicando.ediportal.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter
import org.springframework.web.cors.CorsConfiguration


/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class WebSecurityConfig(private val customUserDetailsService: CustomUserDetailsService,
                        private val unauthorizedHandler: JwtAuthenticationEntryPoint,
                        private val jwtAuthenticationFilter: JwtAuthenticationFilter,
                        private val switchUserService: SwitchUserService) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService<UserDetailsService>(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        try {
//            return super.userDetailsServiceBean()
            return customUserDetailsService
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Bean
    fun switchUserFilter(): SwitchUserFilter {
        val filter = SwitchUserFilter()
        filter.setUserDetailsService(customUserDetailsService)
        filter.setSwitchUserUrl("/switchUser")
        filter.setExitUserUrl("/switchUser/exit")
        filter.setSuccessHandler(switchUserService)
//        filter.setFailureHandler(authenticationFailureHandler())
        return filter
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // CROSS ORIGIN CONFIG
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = mutableListOf("*")
        corsConfiguration.allowedHeaders = mutableListOf("*")
        corsConfiguration.allowedMethods = mutableListOf("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
        http.cors().configurationSource { corsConfiguration }

        http
                .csrf().disable()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        http
                .addFilterAfter(switchUserFilter(), FilterSecurityInterceptor::class.java)
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                .permitAll()
                .antMatchers("/registration/activation")
                .permitAll()
                .antMatchers("/error")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/switchUser").hasRole("ADMIN")
//                .antMatchers("/switchUser/exit").hasRole("PREVIOUS_ADMINISTRATOR")
                .anyRequest()
                .authenticated()

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}