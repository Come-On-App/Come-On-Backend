package com.comeon.backend.api.meeting.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.api.meetingdate.MeetingDateVotingController;
import com.comeon.backend.meeting.presentation.api.meetingdate.VotingAddRequest;
import com.comeon.backend.meeting.presentation.api.meetingdate.VotingRemoveRequest;
import com.comeon.backend.meeting.command.application.v1.VotingDateFacade;
import com.comeon.backend.meeting.query.application.v1.DateVotingQueryService;
import com.comeon.backend.meeting.query.dao.DateVotingDao;
import com.comeon.backend.meeting.query.dto.DateVotingDetails;
import com.comeon.backend.meeting.query.dto.VotingMemberSimple;
import com.comeon.backend.meeting.query.dto.DateVotingSimple;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebMvcTest(MeetingDateVotingController.class)
public class MeetingDateVotingControllerTest extends RestDocsTestSupport {

    @MockBean
    DateVotingDao dateVotingDao;

    @MockBean
    DateVotingQueryService dateVotingQueryService;

    @MockBean
    VotingDateFacade votingDateFacade;

    String baseEndpoint = "/api/v1/meetings/{meeting-id}/date/voting";
    Long mockMeetingId = 117L;

    @Nested
    @DisplayName("모임일 투표 추가 API")
    class votingAdd {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: 인증 필요, RequestBody - 투표 일자 -> then: HTTP 200")
        void success() throws Exception {
            //given
            VotingAddRequest request = new VotingAddRequest("2023-03-13");
            BDDMockito.willDoNothing().given(votingDateFacade)
                    .addVoting(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.any());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint, mockMeetingId)
                            .content(createJson(request))
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("투표를 등록할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("투표를 등록할 모임 일자. +\nyyyy-MM-dd 형식의 날짜 지정.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("투표가 성공적으로 등록되었는지 여부")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임일 투표 삭제 API")
    class votingRemove {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: 인증 필요, RequestBody - 투표 일자 -> then: HTTP 200")
        void success() throws Exception {
            //given
            VotingRemoveRequest request = new VotingRemoveRequest("2023-03-13");
            BDDMockito.willDoNothing().given(votingDateFacade)
                    .removeVoting(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.any());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.delete(endpoint, mockMeetingId)
                            .content(createJson(request))
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("삭제할 모임일 투표가 포함된 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("투표를 삭제할 모임 일자. +\nyyyy-MM-dd 형식의 날짜 지정.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("투표가 성공적으로 삭제되었는지 여부")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임일 투표 현황 리스트 조회 API")
    class votingSimpleList {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: 인증 필요, RequestBody - 투표 일자 -> then: HTTP 200")
        void success() throws Exception {
            //given
            BDDMockito.given(dateVotingDao.findVotingSimpleListByMeetingIdWhetherMyVoting(BDDMockito.anyLong(), BDDMockito.anyLong()))
                    .willReturn(
                            List.of(
                                    new DateVotingSimple(LocalDate.of(2023, 3,1), 1, true),
                                    new DateVotingSimple(LocalDate.of(2023, 3,2), 1, false),
                                    new DateVotingSimple(LocalDate.of(2023, 3,8), 5, true),
                                    new DateVotingSimple(LocalDate.of(2023, 3,9), 1, false),
                                    new DateVotingSimple(LocalDate.of(2023, 3,10), 2, false),
                                    new DateVotingSimple(LocalDate.of(2023, 3,13), 4, true)
                            )
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint, mockMeetingId)
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("투표 현황을 조회할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("contents").withSubsectionId("list-contents"),
                                    getTitleAttributes("contents 응답 필드"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("모임일 투표 일자"),
                                    PayloadDocumentation.fieldWithPath("memberCount").type(JsonFieldType.NUMBER).description("해당 일자에 투표한 회원의 수"),
                                    PayloadDocumentation.fieldWithPath("myVoting").type(JsonFieldType.BOOLEAN).description("현재 유저의 투표 여부")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("특정 일자 투표 현황 상세 조회 API")
    class votingDetails {

        String endpoint = baseEndpoint + "/details";

        @Test
        @DisplayName("given: 인증 필요, RequestBody - 투표 일자 -> then: HTTP 200")
        void success() throws Exception {
            //given
            LocalDate date = LocalDate.of(2023, 3, 8);
            BDDMockito.given(dateVotingQueryService.votingDetails(BDDMockito.anyLong(), BDDMockito.any(), BDDMockito.anyLong()))
                    .willReturn(
                            new DateVotingDetails(
                                    date,
                                    true,
                                    List.of(
                                            new VotingMemberSimple(123L, "user_123", "https://xxx.xxxxx.xxx/xxxxxxx", MemberRole.HOST.name()),
                                            new VotingMemberSimple(242L, "user_242", "https://xxx.xxxxx.xxx/xxxxxxx", MemberRole.PARTICIPANT.name()),
                                            new VotingMemberSimple(257L, "user_257", "https://xxx.xxxxx.xxx/xxxxxxx", MemberRole.PARTICIPANT.name()),
                                            new VotingMemberSimple(277L, "user_277", "https://xxx.xxxxx.xxx/xxxxxxx", MemberRole.PARTICIPANT.name()),
                                            new VotingMemberSimple(278L, "user_278", "https://xxx.xxxxx.xxx/xxxxxxx", MemberRole.PARTICIPANT.name())
                                    )
                            )
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint, mockMeetingId)
                            .param("date", date.format(DateTimeFormatter.ISO_DATE))
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("특정 일자의 투표 내역을 조회할 모임의 식별값")
                            ),
                            RequestDocumentation.requestParameters(
                                    getTitleAttributes("요청 파라미터"),
                                    RequestDocumentation.parameterWithName("date").description("조회할 일자. +\nyyyy-MM-dd 형식의 날짜 지정.")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("모임일 투표 일자"),
                                    PayloadDocumentation.fieldWithPath("memberCount").type(JsonFieldType.NUMBER).description("해당 일자에 투표한 회원의 수"),
                                    PayloadDocumentation.fieldWithPath("myVoting").type(JsonFieldType.BOOLEAN).description("현재 유저의 투표 여부"),
                                    PayloadDocumentation.fieldWithPath("votingUsers").type(JsonFieldType.ARRAY).description("투표한 회원들의 정보"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].userId").type(JsonFieldType.NUMBER).description("회원의 유저 식별값"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].nickname").type(JsonFieldType.STRING).description("회원의 유저 닉네임"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].profileImageUrl").type(JsonFieldType.STRING).description("회원의 프로필 이미지 URL"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].memberRole").type(JsonFieldType.STRING).description("회원의 모임 권한 +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }
}
