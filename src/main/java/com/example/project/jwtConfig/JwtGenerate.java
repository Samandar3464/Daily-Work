package com.example.project.jwtConfig;

import com.example.project.entity.Person;
import com.example.project.exception.RefreshTokeNotFound;
import com.example.project.exception.TimeExceededException;
import com.example.project.exception.UserNotFoundException;
import com.example.project.repository.PersonRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.project.entity.Constants.*;

@Service
@RequiredArgsConstructor
public class JwtGenerate {
    private final PersonRepository personRepository;

    private static final String JWT_ACCESS_KEY = "404E635266556A586E327235753878F413F4428472B4B6250645367566B5970";
    private static final String JWT_REFRESH_KEY = "404E635266556A586E327235753878F413F4428472B4B6250645lll367566B5970";
    private static final long accessTokenLiveTime = 10 * 60 * 100000;
    private static final long reFreshTokenLiveTime = 1_00000 * 60 * 60 * 24;

    public static synchronized String generateAccessToken(
            Person person
    ) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_KEY)
                .setSubject(person.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenLiveTime))
                .claim(AUTHORITIES, person.getAuthorities())
                .compact();
    }

    public static synchronized String generateRefreshToken( Person person) {
        return REFRESH_TOKEN + Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_REFRESH_KEY)
                .setSubject(person.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + reFreshTokenLiveTime))
                .compact();
    }

    public static synchronized Claims isValidAccessToken(String token) {
        return getAccessClaim(token);
    }

    public static synchronized Claims isValidRefreshToken(String token) {
        return getRefreshClaim(token);
    }

    public String checkRefreshTokenValidAndGetAccessToken(HttpServletRequest request) throws Exception {
        String requestHeader = request.getHeader(AUTHORIZATION);
        if (requestHeader == null || !requestHeader.startsWith(REFRESH_TOKEN)) {
            throw new RefreshTokeNotFound(REFRESH_TOKEN_NOT_FOUND);
        }
        String token = requestHeader.replace(REFRESH_TOKEN, "");
        Claims claims = JwtGenerate.isValidRefreshToken(token);
        if (claims == null) {
            throw new Exception();
        }
        Person person= personRepository.findByPhoneNumber(claims.getSubject()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return JwtGenerate.generateAccessToken(person);
    }

    private static synchronized Claims getAccessClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_ACCESS_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw new TimeExceededException(TOKEN_TIME_OUT);
        }
    }

    public static synchronized boolean isValid(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(JWT_ACCESS_KEY).parseClaimsJws(token).getBody();
            if (body != null)
                return true;
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw e;
        }
        return false;
    }

    private static synchronized Claims getRefreshClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_REFRESH_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw e;
        }
    }
}
