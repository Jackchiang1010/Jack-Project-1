package com.example.jackstylish.util;

import com.example.jackstylish.dto.user.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    // Token expiration date
    @Value("${jwt.expiration}")
    public Integer EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDto userDto) {

        Map<String, Object> extractClaims = new HashMap<>();

        extractClaims.put("id", userDto.getId());
        extractClaims.put("provider", userDto.getProvider());
        extractClaims.put("name", userDto.getName());
        extractClaims.put("email", userDto.getEmail());
        extractClaims.put("password", userDto.getPassword());
        extractClaims.put("picture", userDto.getPicture());

        return generateToken(extractClaims, userDto);
    }

    /**
     * 簽發Token
     */
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDto userDto
    ) {

        log.info("userDto : {}", userDto);

        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDto.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 驗證Token有效性，比對JWT和UserDetails的Username(Email)是否相同
     *
     * @return 有效為True，反之False
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = extractExpiration(token);
//        return extractExpiration(token).before(new Date());
        return expirationDate != null && expirationDate.before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 獲取令牌中所有的聲明
     *
     * @return 令牌中所有的聲明
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("token expired");
            return e.getClaims();
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
//            log.info("token not valid");
//            throw e;
        } catch (Exception e) {
            log.info("token not valid!");
            throw e;
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
