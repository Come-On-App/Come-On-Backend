package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.application.GoogleUserService;
import com.comeon.backend.user.application.KakaoUserService;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.domain.Role;
import com.comeon.backend.user.presentation.api.OAuth2LoginController;
import com.comeon.backend.user.presentation.api.request.GoogleOAuth2LoginRequest;
import com.comeon.backend.user.presentation.api.request.KakaoOAuth2LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.*;

@WebMvcTest({OAuth2LoginController.class})
public class OAuth2LoginControllerTest extends RestDocsTestSupport {

    @MockBean
    GoogleUserService googleUserService;

    @MockBean
    KakaoUserService kakaoUserService;

    @Nested
    @DisplayName("구글 ID-Token으로 로그인 처리")
    class googleOAuth2Login {

        String googleOAuth2LoginEndpoint = "/api/v1/oauth/google";

        @Test
        @DisplayName("given: 유효한 idToken -> then: HTTP 200")
        void success() throws Exception {
            //given
            GoogleOAuth2LoginRequest request = new GoogleOAuth2LoginRequest("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            // mocking
            Tokens tokens = new Tokens(
                    jwtGenerator.initBuilder(TokenType.ACCESS)
                            .userId(1L)
                            .nickname("userNickname")
                            .authorities(Role.USER.getValue())
                            .build(),
                    jwtGenerator.initBuilder(TokenType.REFRESH)
                            .build()
            );
            given(googleUserService.login(anyString())).willReturn(tokens);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(googleOAuth2LoginEndpoint)
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.expiry").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(1L))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.expiry").isNotEmpty());

            // docs
            perform.andDo(
                    restDocs.document(
                            PayloadDocumentation.requestFields(
                                    Attributes.attributes(Attributes.key("title").value("요청 필드")),
                                    PayloadDocumentation.subsectionWithPath("idToken").type(JsonFieldType.STRING).description("구글 로그인 성공시 응답으로 받은 ID-Token. Bearer는 붙이지 않습니다.")
                            ),
                            PayloadDocumentation.responseFields(
                                    Attributes.attributes(Attributes.key("title").value("응답 필드")),
                                    PayloadDocumentation.subsectionWithPath("accessToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 엑세스 토큰"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 리프레시 토큰")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("accessToken").withSubsectionId("ACC-TOKEN"),
                                    Attributes.attributes(Attributes.key("title").value("accessToken의 각 필드")),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태."),
                                    PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("로그인 처리된 유저의 식별값")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("refreshToken").withSubsectionId("REF-TOKEN"),
                                    Attributes.attributes(Attributes.key("title").value("refreshToken의 각 필드")),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태.")
                            )
                    )
            );
        }

//        @Test
//        @DisplayName("given: 유효하지 않은 idToken -> then: HTTP 400, errorCode 2501")
//        void invalidIdToken() throws Exception {
//            //given
//            GoogleOAuth2LoginRequest request = new GoogleOAuth2LoginRequest("invalid-id-token");
//
//            // mocking
//            given(googleAuthService.login(anyString()))
//                    .willThrow(new RestApiException(UserErrorCode.GOOGLE_ID_TOKEN_INVALID));
//
//            //when
//            ResultActions perform = mockMvc.perform(
//                    RestDocumentationRequestBuilders.post(googleOAuth2LoginEndpoint)
//                            .content(createJson(request))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .characterEncoding(StandardCharsets.UTF_8)
//            );
//
//            //then
//            perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(2501));
//
//            // docs
//            perform.andDo(restDocs.document(errorResponseSnippet));
//        }
//
//        @Test
//        @DisplayName("given: 비어있는 토큰 idToken -> then: HTTP 400, errorCode 1002")
//        void validError() throws Exception {
//            // given
//            GoogleOAuth2LoginRequest request = new GoogleOAuth2LoginRequest("");
//
//            // when
//            ResultActions perform = mockMvc.perform(
//                    RestDocumentationRequestBuilders.post(googleOAuth2LoginEndpoint)
//                            .content(createJson(request))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .characterEncoding(StandardCharsets.UTF_8)
//            );
//
//            // then
//            perform.andExpect(MockMvcResultMatchers.status().isBadRequest())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(1002));
//
//            // docs
//            perform.andDo(restDocs.document(validErrorSnippets));
//        }
    }

    @Nested
    @DisplayName("카카오 인가 코드로 로그인 처리")
    class kakaoOAuth2Login {

        String kakaoOAuth2LoginEndpoint = "/api/v1/oauth/kakao";

        @Test
        @DisplayName("given: 유효한 인가코드 -> then: HTTP 200")
        void success() throws Exception {
            //given
            KakaoOAuth2LoginRequest request = new KakaoOAuth2LoginRequest("45n67yw45gtqv34ymms5e4egyw34");

            // mocking
            Tokens tokens = new Tokens(
                    jwtGenerator.initBuilder(TokenType.ACCESS)
                            .userId(1L)
                            .nickname("userNickname")
                            .authorities(Role.USER.getValue())
                            .build(),
                    jwtGenerator.initBuilder(TokenType.REFRESH)
                            .build()
            );
            given(kakaoUserService.login(anyString())).willReturn(tokens);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(kakaoOAuth2LoginEndpoint)
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.expiry").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(1L))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.expiry").isNotEmpty());

            // docs
            perform.andDo(
                    restDocs.document(
                            PayloadDocumentation.requestFields(
                                    Attributes.attributes(Attributes.key("title").value("요청 필드")),
                                    PayloadDocumentation.subsectionWithPath("code").type(JsonFieldType.STRING).description("카카오 로그인 성공시 응답으로 받은 인가코드")
                            ),
                            PayloadDocumentation.responseFields(
                                    Attributes.attributes(Attributes.key("title").value("응답 필드")),
                                    PayloadDocumentation.subsectionWithPath("accessToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 엑세스 토큰"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 리프레시 토큰")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("accessToken").withSubsectionId("ACC-TOKEN"),
                                    Attributes.attributes(Attributes.key("title").value("accessToken의 각 필드")),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태."),
                                    PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("로그인 처리된 유저의 식별값")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("refreshToken").withSubsectionId("REF-TOKEN"),
                                    Attributes.attributes(Attributes.key("title").value("refreshToken의 각 필드")),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태.")
                            )
                    )
            );
        }
    }
}
