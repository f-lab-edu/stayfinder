package com.vacation.platform.stayfinder.util;


import com.vacation.platform.stayfinder.StayFinderApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StayFinderApplication.class)
public class UtilTest {



    // aes256 암호화 테스트
    @Test
    public void aes256Test() throws Exception {

        String text = "test0101@naver.com";

        String key = AES256Util.generateKey();
        String iv = AES256Util.generateIV();
        
        System.out.println("key     " + key);
        System.out.println("iv       " + iv);

        String encData = AES256Util.encrypt(text, key, iv);
        System.out.println("encData          " + encData);

        String decData = AES256Util.decrypt(encData, key, iv);

        System.out.println("decData         " + decData);


    }

}
