package com.shop.reservation.model;

import com.shop.reservation.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String password;
    private String phone;

    // dto -> entity로 변환
    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .phone(memberDto.getPhone())
                .build();
    }

}
