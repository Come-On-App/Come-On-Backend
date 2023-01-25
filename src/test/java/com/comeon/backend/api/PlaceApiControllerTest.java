package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.api.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.application.MeetingPlaceFacade;
import com.comeon.backend.meeting.command.domain.PlaceCategory;
import com.comeon.backend.meeting.presentation.PlaceApiController;
import com.comeon.backend.meeting.presentation.request.PlaceAddRequest;
import com.comeon.backend.meeting.presentation.request.PlaceModifyRequest;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dao.result.PlaceListResult;
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
import java.util.List;

@WebMvcTest(PlaceApiController.class)
public class PlaceApiControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingPlaceFacade meetingPlaceFacade;

    @MockBean
    MeetingPlaceDao meetingPlaceDao;

    String defaultEndpoint = "/api/v1/meetings/{meetingId}/places";

    @Nested
    @DisplayName("장소 리스트 조회 API")
    class placeList {

        String endpoint = defaultEndpoint;

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값 -> then: 해당 모임의 장소 리스트 반환")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            BDDMockito.given(meetingPlaceDao.findPlaceList(BDDMockito.anyLong()))
                    .willReturn(
                            List.of(
                                    new PlaceListResult(
                                            396L,
                                            "place1",
                                            "memo1",
                                            123.56,
                                            67.43,
                                            "address1",
                                            1,
                                            PlaceCategory.ETC.name(),
                                            "243crtc23478"
                                    ),
                                    new PlaceListResult(
                                            399L,
                                            "place2",
                                            "memo2",
                                            127.12,
                                            68.33,
                                            "address2",
                                            2,
                                            PlaceCategory.CAFE.name(),
                                            "j6q234gqseth"
                                    )
                            )
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint, meetingId)
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
                                    RequestDocumentation.parameterWithName("meetingId").description("장소 리스트를 조회하려는 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("contents").withSubsectionId("list-contents"),
                                    getTitleAttributes("contents 응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingPlaceId").type(JsonFieldType.NUMBER).description("장소의 식별값"),
                                    PayloadDocumentation.fieldWithPath("placeName").type(JsonFieldType.STRING).description("장소의 이름"),
                                    PayloadDocumentation.fieldWithPath("memo").type(JsonFieldType.STRING).description("장소에 대한 메모").optional(),
                                    PayloadDocumentation.fieldWithPath("lat").type(JsonFieldType.NUMBER).description("장소의 위도값").optional(),
                                    PayloadDocumentation.fieldWithPath("lng").type(JsonFieldType.NUMBER).description("장소의 경도값").optional(),
                                    PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("장소의 주소").optional(),
                                    PayloadDocumentation.fieldWithPath("order").type(JsonFieldType.NUMBER).description("장소 정렬 순서"),
                                    PayloadDocumentation.fieldWithPath("category").type(JsonFieldType.STRING).description("장소 카테고리. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("googlePlaceId").type(JsonFieldType.STRING).description("Google Place API에서 장소의 식별값").optional()
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("장소 추가 API")
    class placeAdd {

        String endpoint = defaultEndpoint;

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값, 장소 정보 데이터 -> then: 생성된 장소 식별값 반환")
        void success() throws Exception {
            //given
            PlaceAddRequest request = new PlaceAddRequest("newPlace123", "here is memo of place", "서울특별시 XX구 YY동 ZZ-ZZZ", 127.8997, 68.123123, PlaceCategory.SPORT.name(), "c4 tg78q364grv7q2r3gfc27q3rg");
            Long meetingId = 333L;
            Long placeId = 453L;
            BDDMockito.given(meetingPlaceFacade.addPlace(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyDouble(), BDDMockito.anyDouble(), BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyString()))
                    .willReturn(placeId);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint, meetingId)
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
                                    RequestDocumentation.parameterWithName("meetingId").description("장소를 추가하려는 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("장소의 이름"),
                                    PayloadDocumentation.fieldWithPath("memo").type(JsonFieldType.STRING).description("장소에 대한 메모").optional(),
                                    PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("장소의 주소").optional(),
                                    PayloadDocumentation.fieldWithPath("lat").type(JsonFieldType.NUMBER).description("장소의 위도값").optional(),
                                    PayloadDocumentation.fieldWithPath("lng").type(JsonFieldType.NUMBER).description("장소의 경도값").optional(),
                                    PayloadDocumentation.fieldWithPath("category").type(JsonFieldType.STRING).description("장소 카테고리. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("googlePlaceId").type(JsonFieldType.STRING).description("Google Place API에서 장소의 식별값").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingPlaceId").type(JsonFieldType.NUMBER).description("추가된 장소의 식별값")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("장소 수정 API")
    class placeModify {

        String endpoint = defaultEndpoint + "/{placeId}";

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값, 수정할 장소 정보 데이터 -> then: 성공 응답 true")
        void success() throws Exception {
            //given
            PlaceModifyRequest request = new PlaceModifyRequest("modifyPlace123", "place memo modify", "서울특별시 YY구 ZZ동 XX-XXXX", 128.0312, 67.00, PlaceCategory.ETC.name(), "c4 tg78q364grv7q2r3gfc27q3rg");
            Long meetingId = 333L;
            Long placeId = 453L;
            BDDMockito.willDoNothing().given(meetingPlaceFacade)
                    .modifyPlace(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyDouble(), BDDMockito.anyDouble(), BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyString());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.put(endpoint, meetingId, placeId)
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
                                    PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("장소의 이름."),
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

    @Nested
    @DisplayName("장소 삭제 API")
    class placeRemove {

        String endpoint = defaultEndpoint + "/{placeId}";

        @Test
        @DisplayName("given: 인증 필수, 경로 변수로 모임 식별값, 삭제할 장소 정보 데이터 -> then: 성공 응답 true")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            Long placeId = 453L;
            BDDMockito.willDoNothing().given(meetingPlaceFacade)
                    .removePlace(BDDMockito.anyLong(), BDDMockito.anyLong());

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.delete(endpoint, meetingId, placeId)
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
                                    RequestDocumentation.parameterWithName("meetingId").description("삭제하려는 장소가 포함된 모임의 식별값"),
                                    RequestDocumentation.parameterWithName("placeId").description("삭제할 장소의 식별값")
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
}
