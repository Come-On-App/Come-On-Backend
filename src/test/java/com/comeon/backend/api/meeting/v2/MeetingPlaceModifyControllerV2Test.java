package com.comeon.backend.api.meeting.v2;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.application.v2.LockMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v2.ModifyMeetingPlaceFacadeV2;
import com.comeon.backend.meeting.command.application.v2.UnlockMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v2.dto.PlaceModifyV2Request;
import com.comeon.backend.meeting.command.domain.PlaceCategory;
import com.comeon.backend.meeting.presentation.api.meetingplace.v2.MeetingPlaceModifyControllerV2;
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

@WebMvcTest(MeetingPlaceModifyControllerV2.class)
public class MeetingPlaceModifyControllerV2Test extends RestDocsTestSupport {

    @MockBean
    LockMeetingPlaceFacade lockMeetingPlaceFacade;

    @MockBean
    UnlockMeetingPlaceFacade unlockMeetingPlaceFacade;

    @MockBean
    ModifyMeetingPlaceFacadeV2 modifyMeetingPlaceFacadeV2;

    String defaultEndpoint = "/api/v2/meetings/{meetingId}/places/{placeId}";

    @Nested
    @DisplayName("장소 락 API")
    class meetingPlaceLock {

        String endpoint = defaultEndpoint + "/lock";

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값, 장소 식별값 -> then: Http 200")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            Long meetingPlaceId = 413L;
            BDDMockito.willDoNothing().given(lockMeetingPlaceFacade)
                    .lockMeetingPlace(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.anyLong());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint, meetingId, meetingPlaceId)
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
                                    RequestDocumentation.parameterWithName("meetingId").description("락을 등록할 장소가 포함된 모임의 식별값"),
                                    RequestDocumentation.parameterWithName("placeId").description("락을 등록할 장소의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
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
    @DisplayName("장소 락 해제 API")
    class meetingPlaceUnlock {

        String endpoint = defaultEndpoint + "/unlock";

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값, 장소 식별값 -> then: Http 200")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            Long meetingPlaceId = 413L;
            BDDMockito.willDoNothing().given(unlockMeetingPlaceFacade)
                    .unlockMeetingPlace(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.anyLong());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint, meetingId, meetingPlaceId)
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
                                    RequestDocumentation.parameterWithName("meetingId").description("락을 해제할 장소가 포함된 모임의 식별값"),
                                    RequestDocumentation.parameterWithName("placeId").description("락을 해제할 장소의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
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
    @DisplayName("장소 수정 및 락 해제 API")
    class meetingPlaceModifyV2 {

        String endpoint = defaultEndpoint;

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값, 장소 식별값 -> then: Http 200")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            Long meetingPlaceId = 413L;
            PlaceModifyV2Request request = new PlaceModifyV2Request("modifyPlace123", "place memo modify", 128.0312, 67.00, "서울특별시 YY구 ZZ동 XX-XXXX", PlaceCategory.ETC.name(), "c4 tg78q364grv7q2r3gfc27q3rg");

            BDDMockito.willDoNothing().given(modifyMeetingPlaceFacadeV2)
                    .modifyMeetingPlace(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.any());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.put(endpoint, meetingId, meetingPlaceId)
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
                                    RequestDocumentation.parameterWithName("meetingId").description("수정하려는 장소가 포함된 모임의 식별값"),
                                    RequestDocumentation.parameterWithName("placeId").description("수정할 장소의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("placeName").type(JsonFieldType.STRING).description("장소의 이름."),
                                    PayloadDocumentation.fieldWithPath("memo").type(JsonFieldType.STRING).description("장소에 대한 메모. 비워두면 빈 값으로 저장됩니다.").optional(),
                                    PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("장소의 주소. 비워두면 빈 값으로 저장됩니다.").optional(),
                                    PayloadDocumentation.fieldWithPath("lat").type(JsonFieldType.NUMBER).description("장소의 위도값. 비워두면 빈 값으로 저장됩니다.").optional(),
                                    PayloadDocumentation.fieldWithPath("lng").type(JsonFieldType.NUMBER).description("장소의 경도값. 비워두면 빈 값으로 저장됩니다.").optional(),
                                    PayloadDocumentation.fieldWithPath("category").type(JsonFieldType.STRING).description("장소 카테고리. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("googlePlaceId").type(JsonFieldType.STRING).description("Google Place API에서 장소의 식별값. 비워두면 빈 값으로 저장됩니다.").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("요청 처리 성공 여부")
                            )
                    )
            );
        }
    }
}
