package com.shop.reservation.service;

import com.shop.reservation.entity.ShopManager;
import com.shop.reservation.model.ShopManagerDto;
import com.shop.reservation.repository.ShopManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final ShopManagerRepository shopManagerRepository;

    /** 매장관리자 회원가입 */
    @Transactional // DB의 CreateId, UpdateId 자동 수정을 위해 사용
    public ShopManager saveManager(ShopManagerDto shopManagerDto) {
        // validation check
        if(!memberValidationCheck(shopManagerDto)) {
            System.out.println("회원정보가 유효하지않습니다.");
            return null;
        }

        // 공백, 전화번호의 '-' 문자 제거
        shopManagerDto.setName(shopManagerDto.getName().trim());
        shopManagerDto.setPassword(shopManagerDto.getPassword().trim());
        shopManagerDto.setPhone(shopManagerDto.getPhone().trim().replaceAll("-", ""));

        // 비밀번호 암호화
        
        // 매장관리자 정보 등록
        ShopManager savedShopManager =
                shopManagerRepository.save(shopManagerDto.toEntity(shopManagerDto));

        return savedShopManager;
    }

    // 회원가입 validation check
    private boolean memberValidationCheck(ShopManagerDto member) {
        // id 미존재 validation check
        if(!ObjectUtils.isEmpty(member.getId())) {
            System.out.println("이미 등록된 회원입니다.");
            return false;
        }

        // 사용자명 validation check
        if (ObjectUtils.isEmpty(member.getName().trim())
                || member.getName().length() > 50) {
            System.out.println("사용자명 길이는 50자로 제한됩니다.");
            return false;
        }

        // 비밀번호 validation check
        if (ObjectUtils.isEmpty(member.getPassword().trim())
                || 8 > member.getPassword().length()
                || member.getPassword().length() > 100) {
            System.out.println("비밀번호는 최소 8자 이상 최대 100자 이하입니다.");
            return false;
        }

        // 전화번호 validation check
        String realPhoneNumber = member.getPhone().trim().replaceAll("-", "");
        // 휴대폰번호가 010,011,017,017,018,019로 시작하고 숫자만으로 총 10 또는 11자리인지 확인
        String phonePattern = "^01[016789]\\d{7,8}$";

        if (ObjectUtils.isEmpty(member.getPhone().trim())
                || !(Pattern.matches(phonePattern, realPhoneNumber))) {
            System.out.println("유효하지 않은 전화번호입니다.");
            return false;
        }
        
        // 전화번호 중복등록 체크
        if(!ObjectUtils.isEmpty(shopManagerRepository.findByPhone(realPhoneNumber))) {
            System.out.println("이미 등록된 전화번호입니다.");
            return false;
        }

        return true;
    }
}
