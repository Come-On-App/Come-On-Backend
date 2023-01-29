package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.jwt.application.JwtReissueFacade;
import com.comeon.backend.jwt.application.JwtToken;
import com.comeon.backend.jwt.application.Tokens;
import com.comeon.backend.jwt.presentation.JwtReissueApiController;
import com.comeon.backend.jwt.presentation.request.JwtReissueRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.*;

@WebMvcTest({JwtReissueApiController.class})
public class JwtReissueApiControllerTest extends RestDocsTestSupport {

    @MockBean
    JwtReissueFacade jwtReissueFacade;

    @Nested
    @DisplayName("앱 토큰 재발급")
    class jwtReissue {

        String jwtReissueEndpoint = "/api/v1/auth/reissue";

        @Test
        @DisplayName("given: 유효한 refresh, 리프레시 토큰 항상 재발급 true -> then: HTTP 200, 모든 토큰 재발급")
        void alwaysAllReissue() throws Exception {
            //given
            JwtToken rtk = jwtManager.buildRefreshToken(currentRequestATK.getPayload().getUserId());
            JwtReissueRequest request = new JwtReissueRequest(rtk.getToken(), true);

            // mocking
            Tokens tokens = jwtManager.buildTokens(currentRequestATK.getPayload().getUserId(), currentRequestATK.getPayload().getNickname(), currentRequestATK.getPayload().getAuthorities());
            given(jwtReissueFacade.reissue(anyString(), anyBoolean())).willReturn(tokens);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(jwtReissueEndpoint)
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.expiry").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(currentUserId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.expiry").isNotEmpty());

            // docs
            perform.andDo(
                    restDocs.document(
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.STRING).description("백엔드에서 발급한 유효한 리프레시 토큰. Bearer는 붙이지 않습니다."),
                                    PayloadDocumentation.subsectionWithPath("reissueRefreshTokenAlways").type(JsonFieldType.BOOLEAN).description("리프레시 토큰을 항상 재발급 할 것인지 여부. +\n" +
                                            "true로 설정하면, 리프레시 토큰 만료 기한이 7일 이상 남았어도 항상 재발급합니다. +\n" +
                                            "false로 설정하면, 리프레시 토큰 만료 기한이 7일 이상으로 남으면 재발급하지 않고 7일 미만일 때 자동으로 재발급합니다. +\n" +
                                            "해당 필드를 입력하지 않을 시, 기본값은 false 입니다.").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("accessToken").type(JsonFieldType.OBJECT).description("재발급된 유저의 엑세스 토큰"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.OBJECT).description("재발급된 유저의 리프레시 토큰").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("accessToken").withSubsectionId("ACC-TOKEN"),
                                    getTitleAttributes("accessToken의 각 필드"),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태."),
                                    PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("로그인 처리된 유저의 식별값")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("refreshToken").withSubsectionId("REF-TOKEN"),
                                    getTitleAttributes("refreshToken의 각 필드"),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태.")
                            )
                    )
            );
        }

        @Test
        @DisplayName("given: 유효한 refresh, 리프레시 토큰 항상 재발급 false, 리프레시 토큰 자동 갱신 조건 만족 false -> then: HTTP 200, 엑세스 토큰만 재발급")
        void atkOnlyReissue() throws Exception {
            //given
            JwtToken rtk = jwtManager.buildRefreshToken(currentRequestATK.getPayload().getUserId());
            JwtReissueRequest request = new JwtReissueRequest(rtk.getToken(), false);

            // mocking
            Tokens tokens = new Tokens(jwtManager.buildAccessToken(currentRequestATK.getPayload().getUserId(), currentRequestATK.getPayload().getNickname(), currentRequestATK.getPayload().getAuthorities()), null);
            given(jwtReissueFacade.reissue(anyString(), anyBoolean())).willReturn(tokens);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(jwtReissueEndpoint)
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.expiry").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(currentUserId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").isEmpty());

            // docs
        }

        @Test
        @DisplayName("given: 유효한 refresh, 리프레시 토큰 항상 재발급 false, 리프레시 토큰 자동 갱신 조건 만족 true -> then: HTTP 200, 모두 재발급")
        void allReissue() throws Exception {
            //given
            JwtToken rtk = jwtManager.buildRefreshToken(currentRequestATK.getPayload().getUserId());
            JwtReissueRequest request = new JwtReissueRequest(rtk.getToken(), false);

            // mocking
            Tokens tokens = jwtManager.buildTokens(currentRequestATK.getPayload().getUserId(), currentRequestATK.getPayload().getNickname(), currentRequestATK.getPayload().getAuthorities());
            given(jwtReissueFacade.reissue(anyString(), anyBoolean())).willReturn(tokens);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(jwtReissueEndpoint)
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.expiry").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(currentUserId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.expiry").isNotEmpty());

            // docs
        }
    }
}
