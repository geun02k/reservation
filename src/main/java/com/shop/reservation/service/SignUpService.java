package com.shop.reservation.service;

import com.shop.reservation.entity.Member;
import com.shop.reservation.exception.SignUpException;
import com.shop.reservation.model.MemberDto;
import com.shop.reservation.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

import static com.shop.reservation.exception.type.SignUpErrorCode.*;

@Service
@RequiredArgsConstructor
public class SignUpService {
    // PasswordEncoder
    // : 인코딩된 패스워드 정보를 DB에 저장하기 위해 사용.
    //   PasswordEncoder 인터페이스를 통해 인코딩을 수행하기 위해서는
    //   어떤 구현체를 사용할 것인지 직접 정의해 빈 등록이 필요.
    //   이는 AppConfig.java 파일에서 정의.
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    /** 회원가입 */
    @Transactional // DB의 CreateId, UpdateId 자동 수정을 위해 사용
    public Member createMember(MemberDto memberDto) {
        // validation check
        memberValidationCheck(memberDto);

        // 공백, 전화번호의 '-' 문자 제거
        memberDto.setName(memberDto.getName().trim());
        memberDto.setPassword(memberDto.getPassword().trim());
        memberDto.setPhone(memberDto.getPhone().trim().replaceAll("-", ""));

        // 비밀번호 암호화
        memberDto.setPassword(
                passwordEncoder.encode(memberDto.getPassword()));

        // 매장관리자 정보 등록
        Member savedMember =
                memberRepository.save(memberDto.toEntity(memberDto));

        // createid, updateid 수정
        // (매장관리자 정보 등록 시 id 미존재로 insert 불가해서 일단 update를 통해 등록)
        savedMember.setCreateId(savedMember.getId());
        savedMember.setUpdateId(savedMember.getId());

        return savedMember;
    }

    // 회원가입 validation check
    private void memberValidationCheck(MemberDto member) throws SignUpException {
        // id 미존재 validation check
        if(!ObjectUtils.isEmpty(member.getId())) {
            throw new SignUpException(ALREADY_REGISTERED_MEMBER);
        }

        // 사용자명 validation check
        if (ObjectUtils.isEmpty(member.getName().trim())
                || member.getName().length() > 50) {
            throw new SignUpException(LIMIT_NAME_CHARACTERS_FROM_1_TO_50);
        }

        // 비밀번호 validation check
        if (ObjectUtils.isEmpty(member.getPassword().trim())
                || 8 > member.getPassword().length()
                || member.getPassword().length() > 100) {
            throw new SignUpException(LIMIT_PASSWORD_CHARACTERS_FROM_8_TO_100);
        }

        // 전화번호 validation check
        String realPhoneNumber = member.getPhone().trim().replaceAll("-", "");
        // 휴대폰번호가 010,011,017,017,018,019로 시작하고 숫자만으로 총 10 또는 11자리인지 확인
        String phonePattern = "^01[016789]\\d{7,8}$";

        if (ObjectUtils.isEmpty(member.getPhone().trim())
                || !(Pattern.matches(phonePattern, realPhoneNumber))) {
            throw new SignUpException(INVALID_PHONE_NUMBER);
        }
        
        // 전화번호 중복등록 체크
        if(!ObjectUtils.isEmpty(memberRepository.findByPhone(realPhoneNumber))) {
            throw new SignUpException(ALREADY_REGISTERED_PHONE_NUMBER);
        }
    }
}
