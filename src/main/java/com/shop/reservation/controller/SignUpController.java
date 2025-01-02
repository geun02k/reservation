package com.shop.reservation.controller;

import com.shop.reservation.entity.Member;
import com.shop.reservation.entity.MemberRole;
import com.shop.reservation.model.MemberDto;
import com.shop.reservation.service.SignUpService;
import com.shop.reservation.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** 회원가입 controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    /** 매장관리자 회원가입 */
    @PostMapping("/manager")
    public ResponseEntity<Member> managerSignUp(@RequestBody MemberDto memberDto) {
        // 사용자별 권한부여
        List<MemberRole> roleList = new ArrayList<>();
        roleList.add(MemberRole.builder()
                        .role(UserType.SHOP_MANAGER)
                        .build());
        memberDto.setRoles(roleList);

        // 회원가입
        return ResponseEntity.ok(signUpService.createMember(memberDto));
    }
}
