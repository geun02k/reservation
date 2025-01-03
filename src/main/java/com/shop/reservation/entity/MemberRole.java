package com.shop.reservation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shop.reservation.type.UserType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Long memberId;

    @Enumerated(EnumType.STRING) // enum값 string으로 저장
    private UserType role;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="memberId", insertable = false, updatable = false) // 테이블 매핑 시 foreign key 지정
    private Member member;

}
