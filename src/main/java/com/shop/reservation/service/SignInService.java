package com.shop.reservation.service;

import com.shop.reservation.entity.Member;
import com.shop.reservation.exception.SignInException;
import com.shop.reservation.model.MemberDto;
import com.shop.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.shop.reservation.exception.type.SignInErrorCode.MISMATCH_PASSWORD;
import static com.shop.reservation.exception.type.SignInErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    /** 로그인 검증 */
    public Member authenticate(MemberDto memberDto) {
        // 1. 핸드폰 번호로 사용자 조회
        Member user = memberRepository.findByPhone(memberDto.getPhone())
                .orElseThrow(() -> new SignInException(USER_NOT_FOUND));

        // 2, 비밀번호 검증
        if(!passwordEncoder.matches(memberDto.getPassword(), user.getPassword())) {
            throw new SignInException(MISMATCH_PASSWORD);
        }

        // 3. 회원정보 반환
        return user;
    }


}
