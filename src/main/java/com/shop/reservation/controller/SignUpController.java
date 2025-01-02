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
        // 사용자 권한부여
        addUserNewRole(UserType.SHOP_MANAGER, memberDto);

        // 회원가입
        return ResponseEntity.ok(signUpService.createMember(memberDto));
    }

    /** 고객 회원가입 */
    @PostMapping("/customer")
    public ResponseEntity<Member> customerSignUp(@RequestBody MemberDto memberDto) {
        // 사용자 권한부여
        addUserNewRole(UserType.CUSTOMER, memberDto);
        // 회원가입
        return ResponseEntity.ok(signUpService.createMember(memberDto));
    }

    // 사용자 신규 권한 추가
    private void addUserNewRole(UserType userType, MemberDto memberDto) {
        List<MemberRole> roleList = new ArrayList<>();
        roleList.add(MemberRole.builder()
                .role(userType)
                .build());
        memberDto.setRoles(roleList);
    }
}
