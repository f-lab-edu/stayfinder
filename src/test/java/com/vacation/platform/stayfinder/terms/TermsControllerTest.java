package com.vacation.platform.stayfinder.terms;

import com.vacation.platform.stayfinder.StayFinderApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SpringBootTest(classes = StayFinderApplication.class)
@AutoConfigureMockMvc
public class TermsControllerTest {

    @Test
    public void test0101() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256); // 256비트 키 생성
        SecretKey secretKey = keyGenerator.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Base64 Key: " + base64Key);
    }

//    @Autowired
//    private MockMvc mockMvc;


//    @Test
//    public void testSaveTerms() throws Exception {
//        // given
//        TermsDto termsDto = new TermsDto(
//                1L,
//                "메인 타이틀",
//                "서브 타이틀",
//                "상세 내용",
//                true,
//                1L,
//                true,
//                true
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String termsDtoJson = objectMapper.writeValueAsString(termsDto);
//
//        // when & then
//        mockMvc.perform(post("/api/v1/terms_main/terms_register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(termsDtoJson))
//                .andExpect(status().isOk());
//    }

}
