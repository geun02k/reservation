package com.shop.reservation.model;

import com.shop.reservation.entity.ShopManager;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopManagerDto {

    private Long id;
    private String name;
    private String password;
    private String phone;

    // dto -> entity로 변환
    public ShopManager toEntity(ShopManagerDto shopManager) {
        return ShopManager.builder()
                    .id(shopManager.getId())
                    .name(shopManager.getName())
                    .password(shopManager.getPassword())
                    .phone(shopManager.getPhone())
                    .build();
    }

}
