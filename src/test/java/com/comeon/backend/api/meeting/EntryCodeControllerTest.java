package com.comeon.backend.api.meeting;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.meeting.presentation.api.entrycode.EntryCodeController;
import com.comeon.backend.meeting.command.application.v1.RenewEntryCodeFacade;
import com.comeon.backend.meeting.command.application.v1.dto.EntryCodeRenewResponse;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.EntryCodeDetails;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebMvcTest({EntryCodeController.class})
public class EntryCodeControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingDao meetingDao;

    @MockBean
    RenewEntryCodeFacade renewEntryCodeFacade;

    @Nested
    @DisplayName("입장 코드 조회 API")
    class entryCodeDetails {

        String endpoint = "/api/v1/meetings/{meeting-id}/entry-code";

        @Test
        @DisplayName("given: 인증 필요, 요청 경로에 meetingId -> then: HTTP 200, 해당 모임의 입장 코드 정보")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;
            String entryCodeMock = "DJE52P";
            LocalDateTime expirationMock = LocalDateTime.of(2023, 1, 30, 23, 11, 30);

            BDDMockito.given(meetingDao.findEntryCodeDetails(BDDMockito.anyLong()))
                    .willReturn(new EntryCodeDetails(meetingIdMock, entryCodeMock, expirationMock));

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint, meetingIdMock)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingId").value(meetingIdMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.entryCode").value(entryCodeMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.expiredAt").value(expirationMock.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

            // docs
            perform.andDo(
                    restDocs.document(
                            RequestDocumentation.pathParameters(
                                    getTitleAttributes(endpoint),
                                    RequestDocumentation.parameterWithName("meeting-id").description("입장 코드를 조회할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("입장 코드를 조회한 모임의 식별값"),
                                    PayloadDocumentation.fieldWithPath("entryCode").type(JsonFieldType.STRING).description("해당 모임의 입장 코드. +\n입장 코드를 한번도 갱신하지 않았으면 null 입니다.").optional(),
                                    PayloadDocumentation.fieldWithPath("expiredAt").type(JsonFieldType.STRING).description("해당 모임의 입장 코드가 만료되는 시간. 만료일. +\nyyyy-MM-dd HH:mm:ss 형식. +\n자세한 내용은 요청/응답 예시를 확인해주세요. +\n입장 코드를 한번도 갱신하지 않았으면 null 입니다.").optional()
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("입장 코드 갱신 API")
    class entryCodeRenew {

        String endpoint = "/api/v1/meetings/{meeting-id}/entry-code";

        @Test
        @DisplayName("given: 인증 필요, 요청 경로에 meetingId -> then: HTTP 200, 갱신된 입장 코드 정보")
        void success() throws Exception {
            //given
            Long meetingIdMock = 55L;
            String entryCodeMock = "AJ4ER9";
            LocalDateTime expirationMock = LocalDateTime.now().plusDays(7);

            grantHost();
            BDDMockito.given(renewEntryCodeFacade.renewEntryCode(BDDMockito.anyLong()))
                    .willReturn(new EntryCodeRenewResponse(meetingIdMock, entryCodeMock, expirationMock));

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint, meetingIdMock)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingId").value(meetingIdMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.entryCode").value(entryCodeMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.expiredAt").value(expirationMock.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

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
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("입장 코드를 갱신한 모임의 식별값"),
                                    PayloadDocumentation.fieldWithPath("entryCode").type(JsonFieldType.STRING).description("갱신된 입장 코드"),
                                    PayloadDocumentation.fieldWithPath("expiredAt").type(JsonFieldType.STRING).description("갱신된 입장 코드가 만료되는 시간. 만료일. +\nyyyy-MM-dd HH:mm:ss 형식. +\n자세한 내용은 요청/응답 예시를 확인해주세요.")
                            )
                    )
            );
        }
    }
}
