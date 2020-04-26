package com.example.jwtsecuritydemo.configuration;

import com.example.jwtsecuritydemo.security.AuthenticationFilter;
import com.example.jwtsecuritydemo.security.AuthorizationFilter;
import com.example.jwtsecuritydemo.services.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.example.jwtsecuritydemo.constants.SecurityConstants.SIGN_UP_URL;

/**
 * This class contains the configuration of Spring Boot security.
 * With this configuration we can specify, among several things,
 * which url we can register the user in, which URLs are protected
 * and the type of session we want to use for authentication.
 *
 * @EnableWebSecurity : This annotation activates the web security integrated in Spring Boot
 * whose configuration we are going to change.
 *
 * SIGN_UP_URL: That constant is used by the configure (HttpSecurity http) method
 * in this class to define the endpoint we can register a user in.
 * In addition, this method is responsible for very important configurations such as:
 *   the configuration of CORS,
 *   the definition of authentication and authorization filters
 *   and also establishes that the session will be stateless (which avoids sending cookies along with the response to handle the session).
 *
 * corsConfigurationSource(): allow all urls to support endpoint CORS and this will allow us to limit it to only some or none
 *
 * configure (AuthenticationManagerBuilder auth): allows us to use a custom implementation of a service to obtain user data when authentication is correct
 *
 */

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private ApplicationUserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public SecurityConfiguration(ApplicationUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
