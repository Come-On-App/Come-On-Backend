package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.api.utils.RestDocsUtil;
import com.comeon.backend.user.command.application.UserCommandService;
import com.comeon.backend.user.presentation.api.UserController;
import com.comeon.backend.user.presentation.api.request.UserModifyRequest;
import com.comeon.backend.user.query.application.UserQueryService;
import com.comeon.backend.user.query.dto.UserDetails;
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

@WebMvcTest({UserController.class})
public class UserControllerTest extends RestDocsTestSupport {

    @MockBean
    UserCommandService userCommandService;

    @MockBean
    UserQueryService userQueryService;

    @Nested
    @DisplayName("내 정보 조회 API")
    class myDetails {

        String endpoint = "/api/v1/users/me";

        @Test
        @DisplayName("given: 인증 필요, -> then: HTTP 200, 요청한 유저 식별값에 매칭되는 유저 정보를 반환")
        void success() throws Exception {
            //given
            BDDMockito.given(userQueryService.findUserDetails(BDDMockito.anyLong()))
                    .willReturn(
                            new UserDetails(
                                    currentRequestATK.getClaims().getUserId(),
                                    currentRequestATK.getClaims().getNickname(),
                                    "https://xxx.xxxx.xxx/xxxxxxxxxxxxxx",
                                    currentRequestATK.getClaims().getAuthorities(),
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(currentRequestATK.getClaims().getUserId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").isNotEmpty())
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
                                    PayloadDocumentation.subsectionWithPath("profileImageUrl").type(JsonFieldType.STRING).description("유저의 프로필 이미지 URL").optional(),
                                    PayloadDocumentation.subsectionWithPath("role").type(JsonFieldType.STRING).description("유저의 권한. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.USER_ROLE)),
                                    PayloadDocumentation.subsectionWithPath("email").type(JsonFieldType.STRING).description("회원가입시 등록한 유저의 이메일 정보").optional(),
                                    PayloadDocumentation.subsectionWithPath("name").type(JsonFieldType.STRING).description("회원가입시 등록한 유저의 이름 정보")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("내 정보 수정 API")
    class modifyMe {

        String endpoint = "/api/v1/users/me";

        @Test
        @DisplayName("given: 인증 필요, 유저 닉네임과 프로필 이미지 URL -> then: HTTP 200, success true 응답")
        void success() throws Exception {
            //given
            UserModifyRequest request = new UserModifyRequest("new_nickname", "https://xxx.xxxx.xxxx/new-profile-image-url");

            BDDMockito.willDoNothing()
                    .given(userCommandService).modifyUser(BDDMockito.anyLong(), BDDMockito.anyString(), BDDMockito.anyString());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.put(endpoint)
                            .content(createJson(request))
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
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("nickname").type(JsonFieldType.STRING)
                                            .description("변경할 유저의 닉네임. 닉네임을 변경하지 않을 시에는 기존 유저의 닉네임을 필수로 입력해주세요."),
                                    PayloadDocumentation.fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).optional()
                                            .description("변경할 프로필 이미지 URL. 프로필 이미지 삭제시에는 해당 필드를 비워두면 됩니다.")
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
