package com.comeon.backend.api.report;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.report.command.application.MeetingReportFacade;
import com.comeon.backend.report.command.application.MeetingReportRequest;
import com.comeon.backend.report.presentation.MeetingReportController;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@WebMvcTest({MeetingReportController.class})
public class MeetingReportControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingReportFacade meetingReportFacade;

    @Nested
    @DisplayName("모임 신고")
    class meetingReport {

        String endpoint = "/api/v1/report/meeting";

        @Test
        @DisplayName("given: 모임 삭제 요청")
        void success() throws Exception {
            //given
            MeetingReportRequest request = new MeetingReportRequest(
                    10L,
                    "10번 모임 부적절한 이미지",
                    "신고 내용 작성",
                    null
            );

            BDDMockito.given(meetingReportFacade.saveMeetingReport(BDDMockito.any(), BDDMockito.anyLong()))
                    .willReturn(33L);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint)
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
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("신고할 모임의 식별값"),
                                    PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                    PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("신고 내용"),
                                    PayloadDocumentation.fieldWithPath("reportImageUrl").type(JsonFieldType.STRING).description("참고용 이미지 URL").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("reportId").type(JsonFieldType.NUMBER).description("생성된 신고 게시물의 식별값")
                            )
                    )
            );
        }
    }
}
