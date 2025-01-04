package com.shop.reservation.config;

import com.shop.reservation.entity.MemberRole;
import com.shop.reservation.service.SignInService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60 * 24;

    private final SignInService signInService;

    /**
     * 토큰생성
     * @param userPk 사용자 핸드폰번호
     * @param id 사용자 ID
     * @param roles 사용자권한 목록
     * @return JWT 토큰
     */
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

    /**
     * 토큰 만료시간 유효성 검증 (토큰의 페이로드 정보 추출해 만료여부확인)
     * @param token 토큰값
     * @return true/false
     */
    public boolean validateToken(String token) {
        // 토큰값이 비어있으면 유효하지 않은 토큰 -> false 반환
        if(!StringUtils.hasText(token)) return false;

        try {
            return !Objects.requireNonNull(parseClaims(token))
                    .getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * JWT토큰정보 -> 스프링시큐리티 인증정보로 변환
     * @param jwt JWT 토큰
     * @return Authentication 스프링에서 지원해주는 형태의 토큰 반환
     */
    @Transactional
    public Authentication getAuthentication(String jwt) {
        // 사용자 조회
        // : 다형성을 이용해 부모클래스가 자식클래스를 담고있다.
        //   즉 반환값으로 Member 객체를 담아 반환한다.
        //   그렇기 때문에 Member 클래스에서
        //   부모메서드를 오버라이딩한 getAuthorities() 메서드를 호출하게 된다.
        UserDetails userDetails =
                signInService.loadUserByUsername(getUserPhone(jwt));
        // 스프링에서 지원해주는 형태의 토큰으로 변경.
        // 유저정보, 비밀번호, 권한정보 전달. (토큰정보에 비밀번호 포함하지 않도록 했으므로 비밀번호 빈값)
        // 권한정보는
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    // JWT 서명 검증을 위한 비밀키 생성
    // : secret key 문자열을 UTF-8 바이트 배열로 변환
    //   -> Keys.hmacShakeyFor()를 사용해서 HMAC-SHA 알고리즘에 맞는 키 객체 생성.
    // 그럼 비밀 키 base64로 암호화해서 등록해놓을 필요없이 plain text 등록해놓으면 되는거 아님?
    private SecretKey getDecodedSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰으로부터 클레임정보 가져오기
    private Claims parseClaims(String token) {
        try {
            // Jwts.parser()로 토큰을 열기위한 도구 세팅!
            // (파서)verifyWith(key)로 서명을 검증할 키를 설정하고
            // (키)build()로 최종적으로 토큰을 열기위한 도구 세팅 완료
            // (JwtParser 객체 생성)parseSignedClaims(jwt)로 jwt문자열을 열어서
            // 서명이 잘 되었는지 확인!
            return Jwts.parser()
                    .verifyWith(getDecodedSecretKey()).build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            return null;
        }
    }

    // generateToken 생성 시 phoneNumber return
    public String getUserPhone(String token) {
        return (String) Objects.requireNonNull(this.parseClaims(token)).get("phone");
    }

}

