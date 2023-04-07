package com.example.project.jwtConfig;

import com.example.project.entity.Person;
import com.example.project.exception.RefreshTokeNotFound;
import com.example.project.exception.UserNotFoundException;
import com.example.project.repository.PersonRepository;
import com.example.project.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final PersonRepository personRepository;
    private final AuthService authService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String requestHeader = request.getHeader(AUTHORIZATION);
        if (requestHeader == null || !requestHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = requestHeader.replace("Bearer ", "");
        Claims claims = JwtGenerate.isValidAccessToken(token);
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = authService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
    public String checkRefreshTokenValidAndGetAccessToken(HttpServletRequest request) throws Exception {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader == null || !requestHeader.startsWith("RefreshToken")) {
            throw new RefreshTokeNotFound("ReFresh token not found");
        }
        String token = requestHeader.replace("RefreshToken ", "");
        Claims claims = JwtGenerate.isValidRefreshToken(token);
        if (claims == null) {
            throw new Exception();
        }
        Person person = personRepository.findByPhoneNumber(claims.getSubject()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return "Bear " + JwtGenerate.generateAccessToken(person);
    }
}
