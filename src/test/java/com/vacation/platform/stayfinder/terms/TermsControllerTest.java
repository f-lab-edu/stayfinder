package com.vacation.platform.stayfinder.terms;

import com.vacation.platform.stayfinder.StayFinderApplication;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StayFinderApplication.class)
@AutoConfigureMockMvc
public class TermsControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testSaveTerms() throws Exception {
        // given
        TermsDto termsDto = new TermsDto(
                "메인 타이틀",
                "서브 타이틀",
                "상세 내용",
                true,
                true,
                1001,
                1
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String termsDtoJson = objectMapper.writeValueAsString(termsDto);

        // when & then
        mockMvc.perform(post("/api/v1/terms_main/terms_register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(termsDtoJson))
                .andExpect(status().isOk());
    }

}
