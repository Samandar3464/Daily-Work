package com.example.project.jwtConfig;

import com.example.project.entity.Person;
import com.example.project.exception.TimeExceededException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class JwtGenerate {
    //    static String jwtAccessSecretKey = "SecretKeyForAccessToken";
//    static String jwtRefreshSecretKey = "SecretKeyForRefreshToken";
    static long expirationAccessTime = 3 * 60 * 1000;
    static long expirationRefreshTime = 1_000 * 60 * 60 * 24;
    static SecretKey jwtAccessSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    static SecretKey jwtRefreshSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static synchronized String generateAccessToken(
            Person person
    ) {
//        System.out.println("access"+jwtAccessSecretKey.toString()+"\n"+"refresh"+jwtRefreshSecretKey.toString());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecretKey)
                .setSubject(person.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationAccessTime))
                .claim("authorities", person.getAuthorities())
                .compact();
    }

    public static synchronized String generateRefreshToken(Person person) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecretKey)
                .setSubject(person.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationRefreshTime))
                .compact();
    }

    public static synchronized Claims isValidAccessToken(String token) {
        return getAccessClaim(token);
    }

    public static synchronized Claims isValidRefreshToken(String token) {
        return getRefreshClaim(token);
    }

    public static List<LinkedHashMap<String, String>> getAuthorities(Claims claims) {
        return (List<LinkedHashMap<String, String>>) claims.get("authorities");
    }

    private static synchronized Claims getAccessClaim(String token)  {
        try{
            return Jwts.parser().setSigningKey(jwtAccessSecretKey).parseClaimsJws(token).getBody();
        }catch (Exception e){
           throw e;
        }
    }

    private static synchronized Claims getRefreshClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtRefreshSecretKey).parseClaimsJws(token).getBody();
        } catch (Exception e){
            throw e;
        }
    }
}
