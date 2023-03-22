package com.comeon.backend.api.user.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.user.api.v1.OauthUserApiController;
import com.comeon.backend.user.command.application.OauthUserFacade;
import com.comeon.backend.user.command.application.dto.AppleOauthRequest;
import com.comeon.backend.user.command.application.dto.GoogleOauthRequest;
import com.comeon.backend.user.command.application.dto.KakaoOauthRequest;
import com.comeon.backend.user.query.UserDao;
import com.comeon.backend.user.query.UserSimple;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest({OauthUserApiController.class})
public class OauthUserApiControllerTest extends RestDocsTestSupport {

    @MockBean
    OauthUserFacade oauthUserFacade;

    @MockBean
    UserDao userDao;

    @BeforeEach
    void initMocking() {
        given(userDao.findUserSimple(anyLong()))
                .willReturn(
                        new UserSimple(
                                currentRequestATK.getPayload().getUserId(),
                                currentRequestATK.getPayload().getNickname(),
                                currentRequestATK.getPayload().getAuthorities()
                        )
                );
    }

    @Nested
    @DisplayName("구글 ID-Token으로 로그인 처리")
    class googleOAuth2Login {

        String googleOAuth2LoginEndpoint = "/api/v1/oauth/google";

        @Test
        @DisplayName("given: 유효한 idToken -> then: HTTP 200")
        void success() throws Exception {
            //given
            GoogleOauthRequest request = new GoogleOauthRequest("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            // mocking
            given(oauthUserFacade.googleLogin(any())).willReturn(currentUserId);

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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(currentUserId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.expiry").isNotEmpty());

            // docs
            perform.andDo(
                    restDocs.document(
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.subsectionWithPath("idToken").type(JsonFieldType.STRING).description("구글 로그인 성공시 응답으로 받은 ID-Token. Bearer는 붙이지 않습니다.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("accessToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 엑세스 토큰"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 리프레시 토큰")
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
                                    getTitleAttributes("refreshToken의 각 필드,"),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태.")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("카카오 인가 코드로 로그인 처리")
    class kakaoOAuth2Login {

        String kakaoOAuth2LoginEndpoint = "/api/v1/oauth/kakao";

        @Test
        @DisplayName("given: 유효한 인가코드 -> then: HTTP 200")
        void success() throws Exception {
            //given
            KakaoOauthRequest request = new KakaoOauthRequest("45n67yw45gtqv34ymms5e4egyw34");

            // mocking
            given(oauthUserFacade.kakaoLogin(any())).willReturn(currentUserId);

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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken.userId").value(currentUserId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.token").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken.expiry").isNotEmpty());

            // docs
            perform.andDo(
                    restDocs.document(
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.subsectionWithPath("code").type(JsonFieldType.STRING).description("카카오 로그인 성공시 응답으로 받은 인가코드")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("accessToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 엑세스 토큰"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 리프레시 토큰")
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
                                    getTitleAttributes("refreshToken의 각 필드,"),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태.")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("애플 로그인 처리")
    class appleOAuth2Login {

        String appleOAuth2LoginEndpoint = "/api/v1/oauth/apple";

        @Test
        @DisplayName("given: user, identityToken -> then: HTTP 200")
        void success() throws Exception {
            //given
            AppleOauthRequest request = new AppleOauthRequest(
                    "awefcbjawebvtawebitacwebrjvgbawotubcoawebnrco23qiqb5vti2qu3hbcri23q",
                    "23462345623452346123.541235123",
                    "appleuser@email.com",
                    "apple-user1"
            );

            // mocking
            given(oauthUserFacade.kakaoLogin(any())).willReturn(currentUserId);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(appleOAuth2LoginEndpoint)
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
                                    PayloadDocumentation.subsectionWithPath("identityToken").type(JsonFieldType.STRING).description("애플 로그인 성공시 응답으로 받은 identity-token"),
                                    PayloadDocumentation.subsectionWithPath("user").type(JsonFieldType.STRING).description("애플 로그인 성공시 응답으로 받은 유저 식별값"),
                                    PayloadDocumentation.subsectionWithPath("email").type(JsonFieldType.STRING).description("애플 로그인 성공시 응답으로 받은 이메일").optional(),
                                    PayloadDocumentation.subsectionWithPath("name").type(JsonFieldType.STRING).description("애플 로그인 성공시 응답으로 받은 유저명").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("accessToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 엑세스 토큰"),
                                    PayloadDocumentation.subsectionWithPath("refreshToken").type(JsonFieldType.OBJECT).description("로그인 처리된 유저의 리프레시 토큰")
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
                                    getTitleAttributes("refreshToken의 각 필드,"),
                                    PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰 값"),
                                    PayloadDocumentation.fieldWithPath("expiry").type(JsonFieldType.NUMBER).description("토큰의 만료 시간. UNIX TIME 형태.")
                            )
                    )
            );
        }
    }
}
