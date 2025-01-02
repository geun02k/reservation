package com.shop.reservation.config;

import com.shop.reservation.entity.MemberRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60 * 24;

    /** 토큰생성 */
    public String createToken(String userPk, Long id, List<MemberRole> roles) {
        Date now = new Date();

        return Jwts.builder()
                .claim("id", id)
                .claim("phone", userPk)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .signWith(getDecodedSecretKey())
                .compact();
    }

    // JWT 서명 검증을 위한 비밀키 생성
    // : secret key 문자열을 UTF-8 바이트 배열로 변환
    //   -> Keys.hmacShakeyFor()를 사용해서 HMAC-SHA 알고리즘에 맞는 키 객체 생성.
    // 그럼 비밀 키 base64로 암호화해서 등록해놓을 필요없이 plain text 등록해놓으면 되는거 아님?
    private SecretKey getDecodedSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

