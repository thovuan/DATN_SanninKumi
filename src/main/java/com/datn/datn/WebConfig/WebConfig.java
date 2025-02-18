package com.datn.datn.WebConfig;

import com.datn.datn.Model.Users;
import com.datn.datn.Repository.JwtTokenRepository;
import com.datn.datn.Repository.UsersRepository;
import jakarta.servlet.Filter;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebConfig {

//    private final UsersRepository usersRepository;
//    private final JwtTokenRepository jwtTokenRepository;
//
//    public WebConfig(UsersRepository usersRepository, JwtTokenRepository jwtTokenRepository) {
//        this.usersRepository = usersRepository;
//        this.jwtTokenRepository = jwtTokenRepository;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/login", "/home").permitAll();

                    registry.anyRequest().authenticated();
                })

                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))


                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class)


                .oauth2Login(oauth2login -> {
                    oauth2login
//                            .userInfoEndpoint(userInfo -> userInfo.userService(new UserService()))
                            .loginPage("/login")
                            .successHandler((request, response, authentication) ->{
                                System.out.println("OAuth2 Login Success! Redirecting to /profile");
                                response.sendRedirect("/profile");});

                })

//                .formLogin(Customizer.withDefaults())
//                .logout(logout -> logout.logoutSuccessUrl("/"));
                .build();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
