package com.shop.reservation.repository;

import com.shop.reservation.entity.ShopManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopManagerRepository extends JpaRepository<ShopManager, Long> {
    // 일치하는 전화번호를 가진 점장 조회
    Optional<ShopManager> findByPhone(String phone);
}
