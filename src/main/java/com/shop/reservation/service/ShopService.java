package com.shop.reservation.service;

import com.shop.reservation.entity.Member;
import com.shop.reservation.entity.Shop;
import com.shop.reservation.exception.ShopException;
import com.shop.reservation.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.shop.reservation.exception.type.ShopErrorCode.*;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    /**
     * 매장등록
     * @param shop 저장할 매장 객체 정보
     * @return 저장한 매장 객체 정보
     */
    @Transactional
    public Shop registerShop(Shop shop, Member member) {
        // 매장정보 validation check
        shopInsertValidationCheck(shop);
        // 매장정보 저장 및 반환
        return shopRepository.save(shop);
    }

    /**
     * 매장수정
     * @param shop 수정할 매장 객체 정보
     * @param member 수정자(점장) 객체 정보
     * @return 수정한 매장 객체 정보
     */
    @Transactional
    public Shop modifyShop(Shop shop, Member member) {
        // 매장정보 validation check
        shopUpdateValidationCheck(shop, member);
        // 매장정보 수정 및 반환
        return shopRepository.save(shop);
    }

    /**
     * 매장삭제(논리적삭제)
     * @param shop 삭제할 매장 객체 정보
     * @param member 점장 객체 정보
     * @return 논리적 삭제 건 수
     */
    @Transactional
    public Shop removeShop(Shop shop, Member member) {
        // 매장정보 validation check
        shopRemoveValidationCheck(shop, member);
        // delYn 수정
        shop.setDelYn("Y");
        // 매장정보 삭제 및 반환
        return shopRepository.save(shop);
    }

    // 매장등록 시 매장정보 validation check
    private void shopInsertValidationCheck(Shop shop) {
        // id 미존재 validation check
        if(!ObjectUtils.isEmpty(shop.getId())) {
            throw new ShopException(ALREADY_REGISTERED_SHOP);
        }

        shopCommonValidationCheck(shop);
    }

    // 매장수정 시 매장정보 validation check
    private void shopUpdateValidationCheck(Shop shop, Member member) {
        // id validation check
        if(ObjectUtils.isEmpty(shop.getId())) {
            throw new ShopException(NOT_REGISTERED_SHOP);
        }

        Shop foundShop = shopRepository.findById(shop.getId())
                .orElseThrow(() ->
                        new ShopException(NOT_REGISTERED_SHOP));

        // 담당자ID validation check
        if(!Objects.equals(foundShop.getMemberId(), member.getId())) {
            throw new ShopException(NOT_SHOP_IN_CHARGE);
        }

        // 매장식제여부 validation check
        if(foundShop.getDelYn().equals("Y")) {
            throw new ShopException(REMOVED_SHOP);
        }

        shopCommonValidationCheck(shop);
    }

    // 매장삭제 validation check
    private void shopRemoveValidationCheck(Shop shop, Member member) {
        // id validation check
        if(ObjectUtils.isEmpty(shop.getId())) {
            throw new ShopException(NOT_REGISTERED_SHOP);
        }

        Shop foundShop = shopRepository.findById(shop.getId())
                .orElseThrow(() ->
                        new ShopException(NOT_REGISTERED_SHOP));

        // 담당자ID validation check
        if(!Objects.equals(foundShop.getMemberId(), member.getId())) {
            throw new ShopException(NOT_SHOP_IN_CHARGE);
        }
    }

    // 매장 insert, update validation check
    private void shopCommonValidationCheck(Shop shop) {
        // 매장명 validation check
        if (ObjectUtils.isEmpty(shop.getName().trim())
                || shop.getName().length() > 100) {
            throw new ShopException(LIMIT_NAME_CHARACTERS_FROM_1_TO_100);
        }

        // 그룹 카테고리명 validation check
        if (ObjectUtils.isEmpty(shop.getCategoryGroupName().trim())
                || shop.getCategoryGroupName().length() > 50) {
            throw new ShopException(LIMIT_GROUP_CATEGORY_CHARACTERS_FROM_1_TO_50);
        }

        // 전화번호 validation check
        String realTelNumber = shop.getTel().trim().replaceAll("-", "");
        String phonePattern = "\\d{8,14}";

        if (ObjectUtils.isEmpty(shop.getTel().trim())
                || !(Pattern.matches(phonePattern, realTelNumber))) {
            throw new ShopException(INVALID_TEL_NUMBER);
        }

        // 주소 validation check
        if (ObjectUtils.isEmpty(shop.getAddress().trim())
                || shop.getAddress().length() > 200) {
            throw new ShopException(LIMIT_ADDRESS_CHARACTERS_FROM_1_TO_200);
        }

        // 도로명주소 validation check
        if (ObjectUtils.isEmpty(shop.getRoadAddress().trim())
                || shop.getRoadAddress().length() > 200) {
            throw new ShopException(LIMIT_ROAD_ADDRESS_CHARACTERS_FROM_1_TO_200);
        }

        // 위도 validation check
        coordinateValidationCheck(shop.getLatitude());

        // 경도 validation check
        coordinateValidationCheck(shop.getLongitude());

    }

    // 위도, 경도 같은 좌표 validation check
    private void coordinateValidationCheck(BigDecimal x) {
        final int PRECISION = 18; // 전체 숫자길이
        final int SCALE = 15; // 소수점 아래 숫자길이
        int xPrecision = x.precision();
        int xScale = x.scale();

        if ((BigDecimal.ZERO.compareTo(x) >= 1)
                || (xPrecision > PRECISION)
                || (xScale > SCALE)
                || ((xPrecision-xScale) > (PRECISION-SCALE))) {
            throw new ShopException(LIMIT_PRECISION_LENGTH_18);
        }
    }

}
