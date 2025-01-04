package com.shop.reservation.security.config;

import com.shop.reservation.security.exception.handler.CustomAccessDeniedHandler;
import com.shop.reservation.security.exception.handler.CustomAuthenticationEntryPoint;
import com.shop.reservation.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// @EnableWebSecurity
//  스프링 시큐리티를 활성화하고 웹 보안 설정 구성에 사용됨.
//  자동으로 스프링 시큐리티 필터 체인을 생성하고 웹 보안 활성화.
//  웹 보안 활성화 : 웹 보안 설정 활성화. 스프링 시큐리티의 필터 체인이 동작하여 요청을 인가하고 인증.
//  스프링 시큐리티 구성 : 스프링 시큐리티 구성을 위한 WegbSecurityConfigurer 빈 생성 / 구성 클래스에서 configure() 메서드를 오버라이딩하여 웹 보안 설정 구성가능.
//  다양한 보안 기능 추가 ex) 폼 기반 인증, 로그인 페이지 구성, 권한 설정 등 가능.
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JWT 를 이용한 인증을 위한 필터
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /** 인증관련설정
     *  : 자원별 접근권한 설정 */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호를 비활성화
                // : HTTP POST로 직접 엔드포인트 호출 시 기본적으로는 CSRF 보호를 비활성화 필요.
                .csrf(AbstractHttpConfigurer::disable)
                // 접근경로허가
                .authorizeHttpRequests((authorizeRequests) ->
                                authorizeRequests
                                        // 회원가입, 로그인 경로에 대해 모든인원 접근허용
                                        .requestMatchers("/signup/**", "/signin/**").permitAll()
                                        // 그 외 경로에 대해 인증필요
                                        .anyRequest().authenticated())
                // 이거 안하니까 인증이 필요한 경로 api 호출이 제대로 안됨.
                // id, password 인증 전에
                // jwt 토큰  -> 시큐리티 컨텍스트 인증정보로 변환
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 스프링 시큐리티 예외처리
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(this.authenticationEntryPoint)
                                .accessDeniedHandler(this.accessDeniedHandler));

        return http.build();
    }

}
