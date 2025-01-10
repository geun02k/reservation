package com.shop.reservation.controller;

import com.shop.reservation.entity.Member;
import com.shop.reservation.entity.Shop;
import com.shop.reservation.exception.ShopException;
import com.shop.reservation.exception.type.ShopErrorCode;
import com.shop.reservation.model.ShopSearchRequestDto;
import com.shop.reservation.model.ShopSearchResponseDto;
import com.shop.reservation.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    /**
     * 매장등록 (매장 관리자 권한)
     */
    @PostMapping
    @PreAuthorize("hasAuthority('SHOP_MANAGER')")
    public ResponseEntity<Shop> registerShop(
            @RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.registerShop(shop));
    }

    /**
     * 매장수정 (매장 관리자 권한)
     */
    @PutMapping
    @PreAuthorize("hasAuthority('SHOP_MANAGER')")
    public ResponseEntity<Shop> modifyShop(
            @RequestBody Shop shop,
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(shopService.modifyShop(shop, member));
    }

    /**
     * 매장삭제 (매장 관리자 권한)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SHOP_MANAGER')")
    public ResponseEntity<String> removeShop(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {
        Shop removedShop = shopService.removeShop(id, member);
        if (ObjectUtils.isEmpty(removedShop)) {
            throw new ShopException(ShopErrorCode.FAIL_REMOVE_SHOP);
        }
        return ResponseEntity.ok("삭제되었습니다.");
    }

    /** 매장목록조회 (고객 권한) */
    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> searchShopList(
            @RequestBody ShopSearchRequestDto requestDto,
            final Pageable pageable) {
        Page<ShopSearchResponseDto> shopPage = shopService.searchShopList(requestDto, pageable);
        return ResponseEntity.ok(shopPage);
    }

}
