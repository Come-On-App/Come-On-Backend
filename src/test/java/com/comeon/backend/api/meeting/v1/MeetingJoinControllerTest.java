package com.comeon.backend.api.meeting.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.api.MeetingJoinController;
import com.comeon.backend.meeting.presentation.api.MeetingJoinRequest;
import com.comeon.backend.meeting.command.application.v1.JoinMeetingFacade;
import com.comeon.backend.meeting.command.application.v1.dto.MeetingJoinResponse;
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

@WebMvcTest({MeetingJoinController.class})
class MeetingJoinControllerTest extends RestDocsTestSupport {

    @MockBean
    JoinMeetingFacade joinMeetingFacade;

    @Nested
    @DisplayName("모임 참가 API")
    class meetingJoin {

        String endpoint = "/api/v1/meetings/join";

        @Test
        @DisplayName("given: 인증 필요, 유효한 모임 코드 -> then: HTTP 200, meetingId, meetingMember(memberId, role)")
        void join() throws Exception {
            //given
            MeetingJoinRequest request = new MeetingJoinRequest("EF25FK");

            // mocking
            long meetingIdMock = 300L;
            long memberIdMock = 4777L;
            String memberRoleMock = MemberRole.PARTICIPANT.name();
            BDDMockito.given(joinMeetingFacade.joinMeeting(BDDMockito.anyLong(), BDDMockito.any()))
                    .willReturn(new MeetingJoinResponse(meetingIdMock, memberIdMock, memberRoleMock));

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingId").value(meetingIdMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember.memberId").value(memberIdMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember.memberRole").value(memberRoleMock));

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("entryCode").type(JsonFieldType.STRING).description("모임 입장 코드")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("가입한 모임의 식별값"),
                                    PayloadDocumentation.subsectionWithPath("meetingMember").type(JsonFieldType.OBJECT).description("가입된 모임 회원 정보")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("meetingMember").withSubsectionId("MEETING-MEMBER"),
                                    getTitleAttributes("meetingMember의 각 필드"),
                                    PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("해당 모임에서의 회원 번호. 유저 식별값인 user-id와 회원 번호인 member-id는 서로 다른 값 입니다."),
                                    PayloadDocumentation.fieldWithPath("memberRole").type(JsonFieldType.STRING).description("해당 모임에서 유저의 권한 +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }
}
