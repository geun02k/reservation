package com.shop.reservation.repository;

import com.shop.reservation.entity.Shop;
import com.shop.reservation.model.ShopSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    // keyword를 포함하고 delYn=N인 매장의 오름차순 정렬
    @Query(value=" select s.id, " +
            "    s.member_id, " +
            "    s.name, " +
            "    s.category_group_name, " +
            "    s.tel, " +
            "    s.address, " +
            "    s.road_address, " +
            "    s.latitude, " +
            "    s.longitude, " +
            "    s.del_yn, " +
            "    s.create_id, " +
            "    s.create_date, " +
            "    s.update_id, " +
            "    s.update_date, " +
            "    cast(ifnull(r.avg_rating, 0) as DOUBLE) as rating, " +
            "    cast(0.0 as DOUBLE) as distance " +
            "    from shop s " +
            "    left join ( " +
            "            select  r1.shop_id, " +
            "                   format(avg(r2.rating), 1) as avg_rating " +
            "    from shop_review r2 " +
            "    left join shop_reservation r1 " +
            "    on r2.reservation_id = r1.id " +
            "    where 1=1 " +
            "    and r2.del_yn = 'N' " + // 리뷰삭제되지않음
            "    and r1.reservation_confirm_yn = 'Y' " + // 예약확정
            "    group by r1.shop_id " +
            ") r " +
            "on s.id = r.shop_id " +
            "where s.name like '%'|| ?1 ||'%' " +
            "and s.del_yn='N' " +
            "order by s.name ",
            countQuery = "select count(*) from shop s where s.name like '%' || ?1 || '%' and s.del_yn='N' ",
            nativeQuery = true)
    Page<ShopSearchResponseDto> findByNameContainsAndDelYnOrderByName(
            String keyword, String delYn, Pageable page);

    // keyword를 포함하고 delYn=N인 매장의 평점 내림차순 정렬
    @Query(value=" select s.id, " +
            "    s.member_id, " +
            "    s.name, " +
            "    s.category_group_name, " +
            "    s.tel, " +
            "    s.address, " +
            "    s.road_address, " +
            "    s.latitude, " +
            "    s.longitude, " +
            "    s.del_yn, " +
            "    s.create_id, " +
            "    s.create_date, " +
            "    s.update_id, " +
            "    s.update_date, " +
            "    cast(ifnull(r.avg_rating, 0) as DOUBLE) as rating, " +
            "    cast(0.0 as DOUBLE) as distance " +
            "    from shop s " +
            "    left join ( " +
            "            select r1.shop_id, " +
            "                   format(avg(r2.rating), 1) as avg_rating " +
            "    from shop_review r2 " +
            "    left join shop_reservation r1 " +
            "    on r2.reservation_id = r1.id " +
            "    where 1=1 " +
            "    and r2.del_yn = 'N' " + // 리뷰삭제되지않음
            "    and r1.reservation_confirm_yn = 'Y' " + // 예약확정
            "    group by r1.shop_id " +
            ") r " +
            "on s.id = r.shop_id " +
            "where s.name like '%'|| ?1 ||'%' " +
            "and s.del_yn='N' " +
            "order by r.avg_rating desc ",
            countQuery = "select count(*) from shop s where s.name like '%' || ?1 || '%' and s.del_yn='N' ",
            nativeQuery = true)
    Page<ShopSearchResponseDto> findByNameContainsAndDelYnOrderByRating(
            String keyword, Pageable pageable);

    // keyword를 포함하고 delYn=N인 매장의 거리 오름차순 정렬
    @Query(value = "select s.id, " +
            "     s.member_id, " +
            "     s.name, " +
            "     s.category_group_name, " +
            "     s.tel, " +
            "     s.address, " +
            "     s.road_address, " +
            "     s.latitude, " +
            "     s.longitude, " +
            "     s.del_yn, " +
            "     s.create_id, " +
            "     s.create_date, " +
            "     s.update_id, " +
            "     s.update_date, " +
            "     cast(ifnull(r.avg_rating, 0) as DOUBLE) as rating, " +
            "     cast(d.distance as DOUBLE) as distance " +
            "from shop s     " +
            "left join (  " +
            "        select s2.id, " +
            "               round((6371*acos(cos(radians(?2))*cos(radians(s2.latitude))*cos(radians(s2.longitude) " +
            "               - radians(?3))+sin(radians(?2))*sin(radians(s2.latitude)))), 2) AS distance " +
            "        from shop s2 " +
            "        where s2.del_yn = 'N' " +
            ") d " +
            "on s.id = d.id " +
            "left join (            " +
            "        select  r1.shop_id, " +
            "                format(avg(r2.rating), 1) as avg_rating " +
            "        from shop_review r2 " +
            "        left join shop_reservation r1 " +
            "        on r2.reservation_id = r1.id " +
            "        where r2.del_yn = 'N' " +  // 리뷰삭제되지않음
            "        and r1.reservation_confirm_yn = 'Y' " + // 예약확정
            "        group by r1.shop_id ) r " +
            "on s.id = r.shop_id     " +
            "where s.del_yn = 'N' " +
            "and s.name like '%'|| ?1 ||'%' " +
            "order by d.distance ",
            countQuery = "select count(*) from shop s where s.name like '%' || ?1 || '%' and s.del_yn='N' ",
            nativeQuery = true)
    Page<ShopSearchResponseDto> findByNameContainsAndDelYnOrderByDistance(
            String keyword, Double lat, Double lnt, Pageable pageable);

}
