package com.shop.reservation.controller;

import com.shop.reservation.entity.Member;
import com.shop.reservation.model.MemberDto;
import com.shop.reservation.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 회원가입 controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    /** 매장관리자 회원가입 */
    @PostMapping("/manager")
    public ResponseEntity<Member> managerSignUp(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(signUpService.createMember(memberDto));
    }
}
