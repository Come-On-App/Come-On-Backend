package com.comeon.backend.api.meeting.v2;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.api.member.v2.MemberDetailsController;
import com.comeon.backend.meeting.query.application.v2.MeetingMemberQueryServiceV2;
import com.comeon.backend.meeting.query.dto.MemberDetails;
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
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebMvcTest(MemberDetailsController.class)
public class MemberDetailsControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingMemberQueryServiceV2 meetingMemberQueryService;

    @Nested
    @DisplayName("모임 회원 리스트 조회 API V2")
    class meetingMemberListV2 {

        String endpoint = "/api/v2/meetings/{meeting-id}/members";

        @Test
        @DisplayName("given: 인증 필요, 요청 경로에 meetingId -> then: HTTP 200, 해당 모임의 회원 정보 리스트")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;
            BDDMockito.given(meetingMemberQueryService.findMemberDetailsList(BDDMockito.anyLong()))
                    .willReturn(
                            List.of(
                                    new MemberDetails(88L, 112L, "user112", "https://xxx.xxx.xxxx/xxxxx", MemberRole.HOST.name()),
                                    new MemberDetails(109L, 134L, "user134", "https://xxx.xxx.xxxx/xxxxx", MemberRole.PARTICIPANT.name()),
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("모임 회원 리스트를 조회할 모임의 식별값")
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
                                    PayloadDocumentation.fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("회원의 유저 프로필 이미지 URL."),
                                    PayloadDocumentation.fieldWithPath("memberRole").type(JsonFieldType.STRING).description("모임에서 해당 회원의 권한. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임에서의 내 회원 정보 조회 API V2")
    class myMemberInfoV2 {

        String endpoint = "/api/v2/meetings/{meeting-id}/members/me";

        @Test
        @DisplayName("given: 인증 필요, 요청 경로에 meetingId -> then: HTTP 200")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;
            BDDMockito.given(meetingMemberQueryService.findMemberDetails(BDDMockito.anyLong(), BDDMockito.anyLong()))
                    .willReturn(
                            new MemberDetails(
                                    333L,
                                    currentUserId,
                                    currentRequestATK.getPayload().getNickname(),
                                    "https://xxx.xxx.xxxx/xxxxx",
                                    MemberRole.PARTICIPANT.name()
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("내 모임원 정보를 조회할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원의 모임 회원 번호. +\n유저 식별값과는 별개의 정보입니다."),
                                    PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원의 유저 식별값."),
                                    PayloadDocumentation.fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원의 유저 닉네임."),
                                    PayloadDocumentation.fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("회원의 유저 프로필 이미지 URL."),
                                    PayloadDocumentation.fieldWithPath("memberRole").type(JsonFieldType.STRING).description("회원의 모임 권한. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }
}
