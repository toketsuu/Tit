package com.tit.tit.security;


import com.tit.tit.service.Impl.UserDetailsServiceImpl;
import com.tit.tit.util.JwtAuthenticationException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final UserDetailsServiceImpl detailsService;

    @Value("${jwt.token.expired}")
    private Long tokenExpired;

    @Value("${jwt.token.secret}")
    private String secret;

    public String createToken(UserDetailsImpl userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUser().getEmail());
        claims.put("email", userDetails.getUser().getEmail());

        Date date = new Date();
        Date validity = new Date(date.getTime() + tokenExpired);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, getEncodeSecret())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.detailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(getEncodeSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(getEncodeSecret()).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid.");
        }
    }

    private String getEncodeSecret(){
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
