package com.comeon.backend.api.meeting.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.meeting.presentation.api.FixedDateListApiController;
import com.comeon.backend.meeting.query.application.FixedDateQueryService;
import com.comeon.backend.meeting.query.dto.FixedDateSimple;
import com.comeon.backend.meeting.query.dto.MeetingFixedDateSummary;
import com.comeon.backend.meeting.query.dto.MeetingFixedDatesResponse;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebMvcTest(FixedDateListApiController.class)
public class FixedDateListApiControllerTest extends RestDocsTestSupport {

    @MockBean
    FixedDateQueryService fixedDateQueryService;

    String baseEndpoint = "/api/v1/meetings/date/confirm";

    @Nested
    @DisplayName("모임별 모임일 조회 API")
    class meetingConfirmDates {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("성공시 응답")
        void success() throws Exception {
            //given
            BDDMockito.given(fixedDateQueryService.findMeetingFixedDates(BDDMockito.anyLong(), BDDMockito.anyInt(), BDDMockito.anyInt()))
                    .willReturn(
                            new MeetingFixedDatesResponse(
                                    List.of(
                                            new MeetingFixedDatesResponse.YearMeetings(
                                                    2022,
                                                    List.of(
                                                            new MeetingFixedDatesResponse.MonthCalendar(
                                                                    11,
                                                                    List.of(
                                                                            new MeetingFixedDateSummary(123L, "meeting-123", new FixedDateSimple(LocalDate.of(2022, 11, 4), LocalDate.of(2022, 11, 4)), LocalTime.of(8,0,0)),
                                                                            new MeetingFixedDateSummary(142L, "meeting-142", new FixedDateSimple(LocalDate.of(2022, 11, 8), LocalDate.of(2022, 11, 8)), LocalTime.of(8,0,0))
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new MeetingFixedDatesResponse.YearMeetings(
                                                    2023,
                                                    List.of(
                                                            new MeetingFixedDatesResponse.MonthCalendar(
                                                                    1,
                                                                    List.of(
                                                                            new MeetingFixedDateSummary(211L, "meeting-211", new FixedDateSimple(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1)), LocalTime.of(19,0,0)),
                                                                            new MeetingFixedDateSummary(151L, "meeting-151", new FixedDateSimple(LocalDate.of(2023, 1, 17), LocalDate.of(2023, 1, 17)), LocalTime.of(21,0,0))
                                                                    )
                                                            ),
                                                            new MeetingFixedDatesResponse.MonthCalendar(
                                                                    2,
                                                                    List.of(
                                                                            new MeetingFixedDateSummary(111L, "meeting-111", new FixedDateSimple(LocalDate.of(2023, 2, 16), LocalDate.of(2023, 2, 16)), LocalTime.of(12,0,0)),
                                                                            new MeetingFixedDateSummary(314L, "meeting-314", new FixedDateSimple(LocalDate.of(2023, 2, 25), LocalDate.of(2023, 2, 25)), LocalTime.of(13,0,0))
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint)
                            .param("calendar", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")))
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk());

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            RequestDocumentation.requestParameters(
                                    getTitleAttributes("요청 파라미터"),
                                    RequestDocumentation.parameterWithName("calendar").description("조회할 기준 년월. +\n해당 년월을 기준으로 이전 6개월, 이후 6개월 까지의 데이터를 조회합니다. +\n입력하지 않으면 현재 일자를 기준으로 조회합니다. +\nyyyy-MM 형식으로 작성해주세요. +\n ex) 2023-01").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("result").type(JsonFieldType.ARRAY).description("조회 결과 데이터를 담은 배열").optional(),
                                    PayloadDocumentation.fieldWithPath("result[].year").type(JsonFieldType.NUMBER).description("조회 결과 데이터가 존재하는 년도"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar").type(JsonFieldType.ARRAY).description("해당 년도의 월별 모임일 정보 배열"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].month").type(JsonFieldType.NUMBER).description("조회 결과 데이터가 존재하는 월"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings").type(JsonFieldType.ARRAY).description("해당 년월에 확정된 모임일 배열"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings[].meetingId").type(JsonFieldType.NUMBER).description("모임 식별값"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings[].meetingName").type(JsonFieldType.STRING).description("모임 이름"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings[].fixedDate").type(JsonFieldType.OBJECT).description("모임일 정보"),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings[].fixedDate.startDate").type(JsonFieldType.STRING).description("모임 시작일 정보. +\nyyyy-MM-dd 형식으로 응답."),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings[].fixedDate.endDate").type(JsonFieldType.STRING).description("모임 종료일 정보. +\nyyyy-MM-dd 형식으로 응답."),
                                    PayloadDocumentation.fieldWithPath("result[].calendar[].meetings[].meetingStartTime").type(JsonFieldType.STRING).description("모임 시작 시간. +\nHH:mm:ss 형식으로 응답.")
                            )
                    )
            );
        }
    }
}
