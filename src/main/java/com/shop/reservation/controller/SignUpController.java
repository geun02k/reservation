package com.shop.reservation.controller;

import com.shop.reservation.entity.ShopManager;
import com.shop.reservation.model.ShopManagerDto;
import com.shop.reservation.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 회원가입 controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    /** 매장관리자 회원가입 */
    @PostMapping("/manager")
    public ResponseEntity<ShopManager> managerSignUp(@RequestBody ShopManagerDto shopManager) {
        return ResponseEntity.ok(signUpService.saveManager(shopManager));
    }
}
