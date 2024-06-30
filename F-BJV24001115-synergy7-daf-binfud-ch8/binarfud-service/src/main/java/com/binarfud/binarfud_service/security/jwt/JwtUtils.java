package com.binarfud.binarfud_service.security.jwt;

import com.binarfud.binarfud_service.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username;
        if (authentication.getPrincipal() instanceof UserDetailsImpl userPrincipal) {
            username = userPrincipal.getUsername();
        } else if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            username = oidcUser.getEmail();
        }  else if (authentication.getPrincipal() instanceof DefaultOAuth2User defaultOAuth2User) {
            username = defaultOAuth2User.getAttribute("login");
        }
        else {
            throw new IllegalArgumentException("Unsupported principal type");
        }
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpiration))
                .signWith(getSignKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        return Optional.ofNullable(token)
                .map(t -> {
                    Jwts.parser().setSigningKey(secretKey).parseClaimsJws(t);
                    return true;
                })
                .orElse(false);
    }

    public String getUsernameFromToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getJwtExpirationMs() {
        return jwtExpiration;
    }
}
