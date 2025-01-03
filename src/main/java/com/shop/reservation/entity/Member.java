package com.shop.reservation.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    // 유저:권한 (1:N)
    // 사용자 권한 데이터 member_role 테이블 join해서 가져옴.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member") // 테이블 연관관계 1:N 설정
    @JsonManagedReference
    private List<MemberRole> roles; // 사용자가 여러 권한을 가질 수 있음.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // roles 정보를 SimpleGrantedAuthority로 매핑
        // -> 스프링 시큐리티에서 지원하는 role 관련기능을 쓰기 위함.
        return this.roles.stream()
                .map((MemberRole role) ->
                        new SimpleGrantedAuthority(role.getRole().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

}
