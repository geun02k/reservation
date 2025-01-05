package com.shop.reservation.service;

import com.shop.reservation.entity.Member;
import com.shop.reservation.entity.Shop;
import com.shop.reservation.exception.ShopException;
import com.shop.reservation.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    public Shop registerShop(Shop shop, Member member) {
        // 매장정보 validation check
        shopValidationCheck(shop);

        // 매장정보 저장 및 반환
        return shopRepository.save(shop);
    }


    // 매장정보 validation check
    private void shopValidationCheck(Shop shop) {
        // id 미존재 validation check
        if(!ObjectUtils.isEmpty(shop.getId())) {
            throw new ShopException(ALREADY_REGISTERED_SHOP);
        }

        // 매장명 validation check
        if (ObjectUtils.isEmpty(shop.getName().trim())
                || shop.getName().length() > 100) {
            throw new ShopException(LIMIT_NAME_CHARACTERS_FROM_1_TO_100);
        }
    }
}
