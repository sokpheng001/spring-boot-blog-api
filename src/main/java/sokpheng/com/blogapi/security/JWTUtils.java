package sokpheng.com.blogapi.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {
    private final KeyLoader keyUtil;
    @Value("${jwt.access-token.expire}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expire}")
    private long refreshTokenExpiration;

    /**
     * Generate JWT
     * @param subject
     * @param authorities
     * @return
     */
    public String generateAccessToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        //create a claim
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        claims.put("sub", subject);
        claims.put("roles", roles);
        //
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))// 1 hour
                .signWith(keyUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * Generate refresh token
     * @param subject
     * @return
     */
    public String generateRefreshToken(String subject) {
        //
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // 30 days
                .signWith(keyUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
    // extract subject from jwt token
    public String extractSubject(String token) {
        return getClaimsFromToken(token).getSubject();
    }
    // extract authority from jwt token
    public List<String> extractRoles(String token) {
        Claims claims = getClaimsFromToken(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List<?> rolesList) {
            return rolesList.stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyUtil.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // validate is valid access token type
    public Boolean isAccessTokenType(String token) {
        return getClaimsFromToken(token).get("roles") != null; // if has roles in payload then it's the access token :)
    }
    // valid token by using key-pairs
    public Boolean isTokenValid(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(keyUtil.getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            throw new JwtException("Invalid JWT token");
        }
    }

}