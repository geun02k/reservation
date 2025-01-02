package com.shop.reservation.controller;

import com.shop.reservation.config.JwtAuthenticationProvider;
import com.shop.reservation.entity.Member;
import com.shop.reservation.model.MemberDto;
import com.shop.reservation.service.SignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 로그인 controller */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signin")
public class SignInController {

    private final JwtAuthenticationProvider tokenProvider;

    private final SignInService signInService;

    /** 로그인 (점장, 고객 공통) */
    @PostMapping
    public ResponseEntity<String> signIn(@RequestBody MemberDto memberDto) {
        // 1. 아이디, 패스워드 일치여부 확인
        Member member = signInService.authenticate(memberDto);

        // 2. 토큰 생성
        String token = tokenProvider.createToken(memberDto.getPhone(),
                                                memberDto.getId(),
                                                memberDto.getRoles());
        log.info("user login -> " + memberDto.getId() + ", " + memberDto.getPhone());

        // 3. 토큰 반환
        return ResponseEntity.ok(token);
    }

}
