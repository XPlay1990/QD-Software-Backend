package com.nicando.ediportal.security.config

import com.nicando.ediportal.database.transactions.login.CustomUserDetailsService
import com.nicando.ediportal.security.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


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
class WebSecurityConfig(private val customUserDetailsService: CustomUserDetailsService, private val unauthorizedHandler: JwtAuthenticationEntryPoint) : WebSecurityConfigurerAdapter() {

//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http
//                .authorizeRequests()
//                .antMatchers("/registration").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/register")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//    }

//    @Bean
//    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
//        return JwtAuthenticationFilter()
//    }
//
//
//    @Throws(Exception::class)
//    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
//        authenticationManagerBuilder
//                .userDetailsService<UserDetailsService>(customUserDetailsService)
//                .passwordEncoder(passwordEncoder())
//    }
//
//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    @Throws(Exception::class)
//    override fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
                .permitAll()
                .anyRequest()
                .authenticated()

        // Add our custom JWT security filter
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

    }
}