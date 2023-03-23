package com.comeon.backend.config.security;

import com.comeon.backend.common.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtManager jwtManager;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final AdminKeyProperties adminKeyProperties;

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtManager, jwtAuthenticationProvider);
    }

    private AdminKeyAuthenticationFilter adminKeyAuthenticationFilter() {
        return new AdminKeyAuthenticationFilter(adminKeyProperties);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/h2-console/**", "/docs/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] authWhiteList = {
                "/oauth/callback/**",
                "/api/v1/oauth/**",
                "/api/v1/auth/reissue",
                "/test-api/v1/**",
                "/api/v2/meetings/*/places/lock",
                "/api/v2/meetings/places/unlock"
        };

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(authWhiteList).permitAll()
                .anyRequest().authenticated() // 나머지는 인증된 유저면 허가

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
