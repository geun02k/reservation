package com.shop.reservation.repository;

import com.shop.reservation.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    // keyword를 포함하고 delYn=N인 매장의 오름차순정렬 정렬
    Page<Shop> findByNameContainsAndDelYnOrderByName(String keyword, String delYn, Pageable page);

}
