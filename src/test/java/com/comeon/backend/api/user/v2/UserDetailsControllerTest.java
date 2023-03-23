package com.comeon.backend.api.user.v2;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.common.response.ProfileImageType;
import com.comeon.backend.user.presentation.api.v2.UserDetailsController;
import com.comeon.backend.user.query.UserDetailsResponse;
import com.comeon.backend.user.query.UserQueryService;
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

@WebMvcTest({UserDetailsController.class})
public class UserDetailsControllerTest extends RestDocsTestSupport {

    @MockBean
    UserQueryService userQueryService;

    @Nested
    @DisplayName("내 정보 조회 API V2")
    class myDetailsV2 {

        String endpoint = "/api/v2/users/me";

        @Test
        @DisplayName("given: 인증 필요, -> then: HTTP 200, 요청한 유저 식별값에 매칭되는 유저 정보를 반환")
        void success() throws Exception {
            //given
            BDDMockito.given(userQueryService.findUserDetails(BDDMockito.anyLong()))
                    .willReturn(
                            new UserDetailsResponse(
                                    currentRequestATK.getPayload().getUserId(),
                                    currentRequestATK.getPayload().getNickname(),
                                    ProfileImageType.CUSTOM,
                                    "https://xxx.xxxx.xxx/xxxxxxxxxxxxxx",
                                    currentRequestATK.getPayload().getAuthorities(),
                                    "user-email@email.com",
                                    "user-name"
                            )
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(currentRequestATK.getPayload().getUserId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profileImageType").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profileImageUrl").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.role").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("userId").type(JsonFieldType.NUMBER).description("유저의 식별값"),
                                    PayloadDocumentation.subsectionWithPath("nickname").type(JsonFieldType.STRING).description("유저의 닉네임"),
                                    PayloadDocumentation.subsectionWithPath("profileImageType").type(JsonFieldType.STRING).description("프로필 이미지 응답 타입 정보. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PROFILE_IMAGE_TYPE)),
                                    PayloadDocumentation.subsectionWithPath("profileImageUrl").type(JsonFieldType.STRING).description("유저의 프로필 이미지 URL"),
                                    PayloadDocumentation.subsectionWithPath("role").type(JsonFieldType.STRING).description("유저의 권한. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.USER_ROLE)),
                                    PayloadDocumentation.subsectionWithPath("email").type(JsonFieldType.STRING).description("회원가입시 등록한 유저의 이메일 정보").optional(),
                                    PayloadDocumentation.subsectionWithPath("name").type(JsonFieldType.STRING).description("회원가입시 등록한 유저의 이름 정보")
                            )
                    )
            );
        }
    }
}
