package com.comeon.backend.api.date;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.date.api.FixedDateApiController;
import com.comeon.backend.date.command.application.confirm.DateConfirmFacade;
import com.comeon.backend.date.command.application.confirm.FixedDateAddRequest;
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

@WebMvcTest(FixedDateApiController.class)
public class FixedDateInfoApiControllerTest extends RestDocsTestSupport {

    @MockBean
    DateConfirmFacade dateConfirmFacade;

    String baseEndpoint = "/api/v1/meetings/{meeting-id}/date/confirm";
    Long mockMeetingId = 117L;

    @Nested
    @DisplayName("모임일 확정 API")
    class meetingDateConfirm {

        String endpoint = baseEndpoint;

        @Test
        @DisplayName("given: 인증 필요, RequestBody - 모임 시작일, 종료일, 시작 시간 -> then: HTTP 200")
        void success() throws Exception {
            //given
            FixedDateAddRequest request = new FixedDateAddRequest(
                    LocalDate.of(2023, 3, 5).format(DateTimeFormatter.ISO_DATE),
                    LocalDate.of(2023, 3, 5).format(DateTimeFormatter.ISO_DATE)
            );
            BDDMockito.willDoNothing().given(dateConfirmFacade)
                    .confirmMeetingDate(BDDMockito.anyLong(), BDDMockito.any());
            grantHost();

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
                                    PayloadDocumentation.fieldWithPath("meetingDateStartFrom").type(JsonFieldType.STRING).description("확정할 모임일의 시작 일자. +\nyyyy-MM-dd 형식의 날짜 지정."),
                                    PayloadDocumentation.fieldWithPath("meetingDateEndTo").type(JsonFieldType.STRING).description("확정할 모임일의 종료 일자. +\nyyyy-MM-dd 형식의 날짜 지정.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("투표가 성공적으로 등록되었는지 여부")
                            )
                    )
            );
        }
    }
}
