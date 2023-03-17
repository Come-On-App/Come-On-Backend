package com.comeon.backend.api.meeting.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.meeting.presentation.api.meetingtime.MeetingTimeController;
import com.comeon.backend.meeting.presentation.api.meetingtime.MeetingTimeModifyRequest;
import com.comeon.backend.meeting.command.application.v1.ModifyMeetingTimeFacade;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingTimeSimple;
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
import java.time.LocalTime;

@WebMvcTest({MeetingTimeController.class})
public class MeetingTimeControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingDao meetingDao;

    @MockBean
    ModifyMeetingTimeFacade modifyMeetingTimeFacade;

    @Nested
    @DisplayName("모임 시간 변경 API")
    class meetingTimeModify {

        String endpoint = "/api/v1/meetings/{meeting-id}/meeting-time";

        @Test
        @DisplayName("given: 모임 HOST 권한, 요청 경로에 meetingId, 변경할 시간 정보 -> then: HTTP 200")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;
            MeetingTimeModifyRequest request = new MeetingTimeModifyRequest(LocalTime.of(22, 0, 0));

            grantHost();
            BDDMockito.willDoNothing().given(modifyMeetingTimeFacade)
                    .modifyMeetingTime(BDDMockito.anyLong(), BDDMockito.any());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint, meetingIdMock)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .content(createJson(request))
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("모임 시간을 변경할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingStartTime").type(JsonFieldType.STRING).description("모임 만남 시간. +\nHH:mm:ss 형식.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("요청 처리 성공 여부")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임 시간 조회 API")
    class meetingTimeSimple {

        String endpoint = "/api/v1/meetings/{meeting-id}/meeting-time";

        @Test
        @DisplayName("given: 인증 필요, 요청 경로에 meetingId -> then: HTTP 200, 모임 시간 정보")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;

            BDDMockito.given(meetingDao.findMeetingTimeSimple(BDDMockito.anyLong()))
                    .willReturn(new MeetingTimeSimple(LocalTime.of(13,30,0)));

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
                                    RequestDocumentation.parameterWithName("meeting-id").description("모임 시작 시간을 조회할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingStartTime").type(JsonFieldType.STRING).description("모임 시작 시간. +\nHH:mm:ss 형식")
                            )
                    )
            );
        }
    }
}
