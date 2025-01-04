package com.shop.reservation.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Aes256UtilTest {

    @Test
    @DisplayName("Aes256암복호화")
    void encryptAndDecrypt() {
        // given
        String encrypt = Aes256Util.encrypt("Hello World");

        // when
        String decrypt = Aes256Util.decrypt(encrypt);

        // then
        assertEquals("Hello World", decrypt);
    }
}