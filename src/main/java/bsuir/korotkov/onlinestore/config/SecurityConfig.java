package bsuir.korotkov.onlinestore.config;

import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
    private final AccountDetailsService accountDetailsService;

    private final JWTFilter jwtFilter;

    public SecurityConfig(AccountDetailsService accountDetailsService, JWTFilter jwtFilter) {
        this.accountDetailsService = accountDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers("/api/basket")
                                .hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.POST, "/api/brand")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/type")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .permitAll()
                )
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
