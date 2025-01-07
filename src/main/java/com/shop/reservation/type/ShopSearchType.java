package com.shop.reservation.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ShopSearchType {
    ORDER_BY_NAME(1,"매장명순으로 정렬"),
    ORDER_BY_RATING(2,"별점순으로 정렬"),
    ORDER_BY_DISTANCE(3, "거리순으로 정렬");

    private final int value;
    private final String description;

    public int value() {
        return this.value;
    }
}
