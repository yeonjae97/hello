package kr.co.sukbinggo.hello.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.sukbinggo.hello.model.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
  private static final String SECRET_KEY = "1234";

  public String create(UserEntity userEntity) {
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

    return Jwts.builder()
        // header
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        // payload
        .setSubject(userEntity.getId())
        .setIssuer("myApp")
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .compact();
  }

  public String validateAndGetUserId(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public String create(Authentication authentication) {
    ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User) authentication.getPrincipal();
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

    return Jwts.builder()
      .setSubject(userPrincipal.getName())
      .setIssuedAt(new Date())
      .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .compact();
  }

}
