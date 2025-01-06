package com.shop.reservation.repository;

import com.shop.reservation.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    // keyword를 포함하고 delYn=N인 매장의 오름차순정렬 정렬
    Page<Shop> findByNameContainsAndDelYnOrderByName(String keyword, String delYn, Pageable page);

    // keyword를 포함하고 delYn=N인 매장의 별점 내림차순 정렬
    @Query(value="select s.id, " +
            "    s.name, " +
            "    s.category_group_name, " +
            "    s.latitude, " +
            "    s.longitude, " +
            "    s.member_id, " +
            "    s.road_address, " +
            "    s.address, " +
            "    s.del_yn, " +
            "    ifnull(r.avg_rating, 0) as avg_rating, " +
            "    s.create_date, " +
            "    s.create_id, " +
            "    s.update_date, " +
            "    s.update_id, " +
            "    s.tel " +
            "    from shop s " +
            "    left join ( " +
            "            select  r1.shop_id, " +
            "            format(avg(r2.rating), 1) as avg_rating " +
            "    from shop_review r2 " +
            "    left join shop_reservation r1 " +
            "    on r2.reservation_id = r1.id " +
            "    where 1=1 " +
            "    and r2.del_yn = 'N' " + // 리뷰삭제되지않음
            "    and r1.reservation_confirm_yn = 'Y' " + // 예약확정
            "    group by r1.shop_id " +
            ") r " +
            "    on s.id = r.shop_id " +
            "    where s.name like '%'|| ?1 ||'%' " +
            "    and s.del_yn='N' " +
            "    order by r.avg_rating desc ",
            countQuery = "select count(s) from shop s where s.name like '%' || ?1 || '%'",
            nativeQuery = true)
    Page<Shop> findByNameContainsAndDelYnOrderByRating(String keyword, Pageable pageable);
}
