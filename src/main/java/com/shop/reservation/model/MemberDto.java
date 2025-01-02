package com.shop.reservation.model;

import com.shop.reservation.entity.Member;
import com.shop.reservation.entity.MemberRole;
import com.shop.reservation.type.UserType;
import lombok.*;

import java.util.List;

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

    private List<MemberRole> roles; // 사용자 권한

    // dto -> entity로 변환
    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .phone(memberDto.getPhone())
                .roles(memberDto.getRoles())
                .build();
    }

}
