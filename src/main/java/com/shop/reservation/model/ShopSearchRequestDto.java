package com.shop.reservation.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopSearchRequestDto {

    // 공통 필수 인자
    private int orderByStd;
    private String keyword;

    // 선택 인자
    // orderByStd = 3(거리순정렬) 일때만 사용
    private Double latitude;
    private Double longitude;

}
