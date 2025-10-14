package com.practice.blog.global.jwt;

import com.practice.blog.account.entity.Account;
import com.practice.blog.account.repository.AccountsRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    // application.yml jwt값
    @Value("${jwt.secretKey}")
    private String secretKey;

    // 토큰 만료시간
    private static Long accessTokenExpiration = 1000*60*60L; // 1시간 = 1000(ms->s) * 60(s->m) * 60(m->h)
    private static Long refreshTokenExpiration = 1000*60*60*25*14L; // 2주 = 1000(ms->s) * 60(s->m) * 60(m->h) * 24(h->하루) * 14(2주)



    // 토큰에 포함할 기본 정보, 클레임 키값
    private static final String AUTH_CLAIM = "auth";

    private final AccountsRepository accountsRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // access token 생성
    public String createAccessToken(Account account){
        Date now = new Date();
        return Jwts.builder()
                // 헤더
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 내용
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .setSubject(account.getEmail())
                // 서명
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // refresh token 생성
    public String createRefreshToken(Account account){
        Date now = new Date();
        return Jwts.builder()
                // 헤더
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 내용
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration))
                .setSubject(account.getEmail())
                // 서명
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // redis에 refresh token 저장
    public void saveRefreshToken(Long userId, String refreshToken){
        redisTemplate.opsForValue().set(userId.toString(),refreshToken, Duration.ofMillis(refreshTokenExpiration));
    }


    // access token에서 email 추출
    public String extractEmail(String accessToken){
        if(isValidToken(accessToken)){
            return getClaims(accessToken).getSubject();
        }
        return null;
    }

    // 토큰 검증
    public boolean isValidToken(String token){
        try{
            // secretKey를 사용해 토큰 복호화
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            log.info("Validate token success");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    // token에서 사용자 인증 정보 반환
    public Authentication getAuthentication(String token){
        // 토큰 복호화
        Claims claims = getClaims(token);

        // 토큰에서 정보를 꺼냄
        Set<SimpleGrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails
                .User(claims.getSubject(), "", authorities), token, authorities);
    }

    // payload 반환
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
}
