package com.datn.datn.WebConfig;


import com.datn.datn.Model.User_tokens;
import com.datn.datn.Model.Users;
import com.datn.datn.Repository.JwtTokenRepository;
import com.datn.datn.Repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;
//
//    public JWTAuthenticationFilter(UsersRepository usersRepository, JwtTokenRepository jwtTokenRepository) {
//        this.usersRepository = usersRepository;
//        this.jwtTokenRepository = jwtTokenRepository;
//    }

    //private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //logger.info("JWTAuthenticationFilter triggered"); // Debug log
        // Extract JWT from the Authorization header
        String token = extractToken(request);

        if (token != null && validateToken(token)) {
            Authentication auth = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            //logger.info("JWT authentication successful for token: " + token);
        } else {
            //logger.warn("JWT authentication failed");
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }



    private boolean validateToken(String token) {
        // Implement token validation logic (e.g., expiration, signature)
        Optional<User_tokens> jwtToken = jwtTokenRepository.findByToken(token);

        return jwtToken.isPresent() && jwtToken.get().isActive(); // Token not found or inactive
    // Replace with actual validation
        //return  true;
    }

    private Authentication getAuthentication(String token) {
        // Parse token and retrieve user details
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return "";
            }
        };// Load user details based on token

        // Save token to database
        saveTokenToDatabase(token, userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }



    private void saveTokenToDatabase(String token, UserDetails userDetails) {
        User_tokens jwtToken = new User_tokens();
        jwtToken.setToken(token);
        jwtToken.setIssuedAt(LocalDateTime.now());
        jwtToken.setExpiresAt(LocalDateTime.now().plusHours(2)); // Adjust based on token expiry
        jwtToken.setActive(true);

        Optional<Users> user = usersRepository.findByUsername(userDetails.getUsername());
        user.ifPresent(jwtToken::setUser);

        jwtTokenRepository.save(jwtToken);
    }

    public void invalidateToken(String token) {
        Optional<User_tokens> jwtToken = jwtTokenRepository.findByToken(token);
        jwtToken.ifPresent(t -> {
            t.setActive(false); // Mark as inactive
            jwtTokenRepository.save(t);
        });
    }

}
