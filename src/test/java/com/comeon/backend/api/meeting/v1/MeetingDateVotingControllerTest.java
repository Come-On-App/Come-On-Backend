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
    @DisplayName("????????? ?????? ?????? API")
    class votingAdd {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: ?????? ??????, RequestBody - ?????? ?????? -> then: HTTP 200")
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("????????? ????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????. +\nyyyy-MM-dd ????????? ?????? ??????.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????? ??????????????? ?????????????????? ??????")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("????????? ?????? ?????? API")
    class votingRemove {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: ?????? ??????, RequestBody - ?????? ?????? -> then: HTTP 200")
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("????????? ????????? ????????? ????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("????????? ????????? ?????? ??????. +\nyyyy-MM-dd ????????? ?????? ??????.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????? ??????????????? ?????????????????? ??????")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("????????? ?????? ?????? ????????? ?????? API")
    class votingSimpleList {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: ?????? ??????, RequestBody - ?????? ?????? -> then: HTTP 200")
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("?????? ????????? ????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("contents").withSubsectionId("list-contents"),
                                    getTitleAttributes("contents ?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("memberCount").type(JsonFieldType.NUMBER).description("?????? ????????? ????????? ????????? ???"),
                                    PayloadDocumentation.fieldWithPath("myVoting").type(JsonFieldType.BOOLEAN).description("?????? ????????? ?????? ??????")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("?????? ?????? ?????? ?????? ?????? ?????? API")
    class votingDetails {

        String endpoint = baseEndpoint + "/details";

        @Test
        @DisplayName("given: ?????? ??????, RequestBody - ?????? ?????? -> then: HTTP 200")
        void success() throws Exception {
            //given
            LocalDate date = LocalDate.of(2023, 3, 8);
            BDDMockito.given(dateVotingQueryService.votingDetails(BDDMockito.anyLong(), BDDMockito.any(), BDDMockito.anyLong()))
                    .willReturn(
                            new DateVotingDetails(
                                    date,
                                    true,
                                    List.of(
                                            new VotingMemberSimple(123L, "user_123", null, MemberRole.HOST.name()),
                                            new VotingMemberSimple(242L, "user_242", "https://xxx.xxxxx.xxx/xxxxxxx", MemberRole.PARTICIPANT.name()),
                                            new VotingMemberSimple(257L, "user_257", null, MemberRole.PARTICIPANT.name()),
                                            new VotingMemberSimple(277L, "user_277", null, MemberRole.PARTICIPANT.name()),
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("?????? ????????? ?????? ????????? ????????? ????????? ?????????")
                            ),
                            RequestDocumentation.requestParameters(
                                    getTitleAttributes("?????? ????????????"),
                                    RequestDocumentation.parameterWithName("date").description("????????? ??????. +\nyyyy-MM-dd ????????? ?????? ??????.")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("date").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("memberCount").type(JsonFieldType.NUMBER).description("?????? ????????? ????????? ????????? ???"),
                                    PayloadDocumentation.fieldWithPath("myVoting").type(JsonFieldType.BOOLEAN).description("?????? ????????? ?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("votingUsers").type(JsonFieldType.ARRAY).description("????????? ???????????? ??????"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].userId").type(JsonFieldType.NUMBER).description("????????? ?????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].nickname").type(JsonFieldType.STRING).description("????????? ?????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].profileImageUrl").type(JsonFieldType.STRING).description("????????? ????????? ????????? URL").optional(),
                                    PayloadDocumentation.fieldWithPath("votingUsers[].memberRole").type(JsonFieldType.STRING).description("????????? ?????? ?????? +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }
}
