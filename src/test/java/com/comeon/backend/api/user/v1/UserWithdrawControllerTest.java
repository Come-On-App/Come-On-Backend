package com.comeon.backend.api.user.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.user.command.application.UserFacade;
import com.comeon.backend.user.presentation.api.v1.UserWithdrawController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(UserWithdrawController.class)
public class UserWithdrawControllerTest extends RestDocsTestSupport {

    @MockBean
    UserFacade userFacade;

    @Nested
    @DisplayName("회원 탈퇴 API")
    class userWithdraw {

        String endpoint = "/api/v1/users/me";

        @Test
        @DisplayName("given : 인증 필요 -> then: HTTP 200")
        void success() throws Exception {
            //given
            BDDMockito.willDoNothing().given(userFacade).withdrawUser(BDDMockito.anyLong());
            BDDMockito.willDoNothing().given(jwtManager).logout(BDDMockito.anyLong());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.delete(endpoint)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("요청 처리 성공 여부")
                            )
                    )
            );
        }
    }
}
