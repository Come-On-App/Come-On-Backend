package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.meeting.command.application.MeetingCommandService;
import com.comeon.backend.meeting.command.application.MeetingMemberCommandService;
import com.comeon.backend.meeting.command.application.MeetingMemberSummary;
import com.comeon.backend.meeting.command.domain.MeetingMemberRole;
import com.comeon.backend.meeting.presentation.api.MeetingController;
import com.comeon.backend.meeting.presentation.api.request.MeetingAddRequest;
import com.comeon.backend.meeting.presentation.api.request.MeetingJoinRequest;
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
import java.time.LocalDate;

@WebMvcTest({MeetingController.class})
public class MeetingControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingCommandService meetingCommandService;

    @MockBean
    MeetingMemberCommandService meetingMemberCommandService;

    @Nested
    @DisplayName("모임 등록 API")
    class meetingAdd {

        String endpoint = "/api/v1/meetings";

        @Test
        @DisplayName("given: 인증 필요, 모임 이름, 모임 썸네일 이미지, 달력 시작일, 달력 종료일 -> then: HTTP 200, 생성된 모임 식별값 반환")
        void success() throws Exception {
            //given
            MeetingAddRequest request = new MeetingAddRequest(
                    "Come-On 모임",
                    "https://xxx.xxxxx.xxx/meeting-thumbnail-image-url",
                    LocalDate.of(2023, 1, 20),
                    LocalDate.of(2023,2,28)
            );

            long meetingId = 28L;
            BDDMockito.given(meetingCommandService.addMeeting(BDDMockito.anyLong(), BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.any(), BDDMockito.any()))
                    .willReturn(meetingId);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint)
                            .content(createJson(request))
                            .header(HttpHeaders.AUTHORIZATION, currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingId").value(meetingId));

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingName").type(JsonFieldType.STRING).description("모임의 이름."),
                                    PayloadDocumentation.fieldWithPath("meetingImageUrl").type(JsonFieldType.STRING).description("모임 리스트에 표시될 모임 대표 썸네일 이미지."),
                                    PayloadDocumentation.fieldWithPath("calendarStartFrom").type(JsonFieldType.STRING).description("모임 일자 투표가 가능한 캘린더의 시작일. +\nyyyy-MM-dd 형식의 날짜 지정. +\nex) 2023-01-01"),
                                    PayloadDocumentation.fieldWithPath("calendarEndTo").type(JsonFieldType.STRING).description("모임 일자 투표가 가능한 캘린더의 종료일. +\nyyyy-MM-dd 형식으로 날짜 지정. +\nex) 2023-01-01")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("생성된 모임의 식별값")
                            )
                    )
            );
        }
    }

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
            String memberRoleMock = MeetingMemberRole.PARTICIPANT.name();
            BDDMockito.given(meetingMemberCommandService.joinMeeting(BDDMockito.anyLong(), BDDMockito.anyString()))
                    .willReturn(
                            new MeetingMemberSummary(meetingIdMock, memberIdMock, memberRoleMock)
                    );

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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember.role").value(memberRoleMock));

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
                                    PayloadDocumentation.fieldWithPath("role").type(JsonFieldType.STRING).description("해당 모임에서 유저의 권한")
                            )
                    )
            );
        }
    }
}
