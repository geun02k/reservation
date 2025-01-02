package com.shop.reservation.repository;

import com.shop.reservation.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 일치하는 전화번호를 가진 점장 조회
    Optional<Member> findByPhone(String phone);
}
