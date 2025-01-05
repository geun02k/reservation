package com.shop.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    private String categoryGroupName;

    private String tel;

    private String address;

    private String roadAddress;

    @Column(nullable = false, precision = 18, scale = 15)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 18, scale = 15)
    private BigDecimal longitude;

    @Column(length = 1)
    @ColumnDefault("'N'")
    private String delYn;

}
