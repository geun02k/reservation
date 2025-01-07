package com.shop.reservation.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ShopSearchResponseDto {
    private Long id;

    private Long memberId;

    private String name;

    private String categoryGroupName;

    private String tel;

    private String address;

    private String roadAddress;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Character delYn;

    private Long createId;

    private Timestamp createDate;

    private Long updateId;

    private Timestamp updateDate;

    private Double rating;

    private Double distance;
}