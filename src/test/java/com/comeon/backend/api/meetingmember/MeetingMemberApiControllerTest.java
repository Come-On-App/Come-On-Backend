package com.comeon.backend.api.meetingmember;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.config.web.member.MemberRole;
import com.comeon.backend.meetingmember.api.MeetingMemberApiController;
import com.comeon.backend.meetingmember.query.dao.MeetingMemberDao;
import com.comeon.backend.meetingmember.query.dto.MemberDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebMvcTest({MeetingMemberApiController.class})
class MeetingMemberApiControllerTest extends RestDocsTestSupport {

    @Nested
    @DisplayName("모임 회원 리스트 조회 API")
    class meetingMemberList {

        String endpoint = "/api/v1/meetings/{meeting-id}/members";

        @Test
        @DisplayName("given: 인증 필요, 요청 경로에 meetingId -> then: HTTP 200, 갱신된 입장 코드 정보")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;
            BDDMockito.given(meetingMemberDao.findMemberDetailsList(BDDMockito.anyLong()))
                    .willReturn(
                            List.of(
                                    new MemberDetails(88L, 112L, "user112", "https://xxx.xxx.xxxx/xxxxx", MemberRole.HOST.name()),
                                    new MemberDetails(109L, 134L, "user134", null, MemberRole.PARTICIPANT.name()),
                                    new MemberDetails(111L, 155L, "user155", "https://xxx.xxx.xxxx/xxxxx", MemberRole.PARTICIPANT.name()),
                                    new MemberDetails(135L, 157L, "user157", "https://xxx.xxx.xxxx/xxxxx", MemberRole.PARTICIPANT.name())
                            )
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint, meetingIdMock)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk());

            // docs
            perform.andDo(
                    restDocs.document(
                            RequestDocumentation.pathParameters(
                                    getTitleAttributes(endpoint),
                                    RequestDocumentation.parameterWithName("meeting-id").description("입장 코드를 갱신할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("contents").withSubsectionId("list-contents"),
                                    getTitleAttributes("contents 응답 필드"),
                                    PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원의 모임 회원 번호. +\n유저 식별값과는 별개의 정보입니다."),
                                    PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원의 유저 식별값."),
                                    PayloadDocumentation.fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원의 유저 닉네임."),
                                    PayloadDocumentation.fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("회원의 유저 프로필 이미지 URL.").optional(),
                                    PayloadDocumentation.fieldWithPath("memberRole").type(JsonFieldType.STRING).description("모임에서 해당 회원의 권한. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }
}
