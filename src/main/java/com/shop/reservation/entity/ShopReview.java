package com.shop.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class ShopReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long reservationId;

    private String contents;

    @ColumnDefault("0")
    private int rating;

    @Column(length = 1)
    @ColumnDefault("'N'")
    private String delYn;

}
