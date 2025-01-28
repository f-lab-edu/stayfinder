package com.vacation.platform.stayfinder.terms;

import com.vacation.platform.stayfinder.StayFinderApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StayFinderApplication.class)
@AutoConfigureMockMvc
public class TermsControllerTest {

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
