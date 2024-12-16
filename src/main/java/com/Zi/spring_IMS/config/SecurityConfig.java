package com.Zi.spring_IMS.config;

import com.Zi.spring_IMS.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ADMIN", "MANAGER") //Only ADMIN and MANAGER can delete user resource
                                .requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ADMIN") // only admin can update user profile
                                .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("ADMIN") //ONly Admin can delete product
                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()// (used when sign up/ login)everyone can request authentication
                                .requestMatchers("/users/**").authenticated()//only users who passed the authentication can access User feature
                                .requestMatchers("/products/**").authenticated()//only users who passed the authentication can access Product feature
                                .requestMatchers("/inbound_transaction/**").authenticated()//only users who passed the authentication can access Inbound Transaction feature
                                .requestMatchers("/outbound_transaction/**").authenticated()//only users who passed the authentication can access Outbound Transaction feature
                                .anyRequest().permitAll() // all other request excluded above, allow all users to access

                )
                //add headers setting to allow h2-console UI
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable ))
                // add this for jwt (Configures the app to use stateless sessions by setting SessionCreationPolicy.STATELESS)
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                // add jwt before the username password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Disable CSRF((Cross-Site Request Forgery)) for simplicity -> CSRF protection is often unnecessary for APIs that rely on tokens (e.g., JWT).
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
