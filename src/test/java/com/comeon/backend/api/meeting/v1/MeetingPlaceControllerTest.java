package com.comeon.backend.api.meeting.v1;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.meeting.presentation.api.meetingplace.MeetingPlaceController;
import com.comeon.backend.meeting.command.application.v1.AddMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v1.ModifyMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v1.RemoveMeetingPlaceFacade;
import com.comeon.backend.meeting.command.application.v1.dto.PlaceAddRequest;
import com.comeon.backend.meeting.command.application.v1.dto.PlaceModifyRequest;
import com.comeon.backend.meeting.command.domain.PlaceCategory;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dto.PlaceDetails;
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

@WebMvcTest(MeetingPlaceController.class)
public class MeetingPlaceControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingPlaceDao meetingPlaceDao;

    @MockBean
    AddMeetingPlaceFacade addMeetingPlaceFacade;

    @MockBean
    ModifyMeetingPlaceFacade modifyMeetingPlaceFacade;

    @MockBean
    RemoveMeetingPlaceFacade removeMeetingPlaceFacade;

    String defaultEndpoint = "/api/v1/meetings/{meetingId}/places";

    @Nested
    @DisplayName("?????? ????????? ?????? API")
    class placeList {

        String endpoint = defaultEndpoint;

        @Test
        @DisplayName("given: ?????? ??????, ?????? ????????? ?????? ????????? -> then: ?????? ????????? ?????? ????????? ??????")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            BDDMockito.given(meetingPlaceDao.findPlacesByMeetingId(BDDMockito.anyLong()))
                    .willReturn(
                            List.of(
                                    new PlaceDetails(
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
                                    new PlaceDetails(
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
                                    RequestDocumentation.parameterWithName("meetingId").description("?????? ???????????? ??????????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("contents").withSubsectionId("list-contents"),
                                    getTitleAttributes("contents ?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("meetingPlaceId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("placeName").type(JsonFieldType.STRING).description("????????? ??????"),
                                    PayloadDocumentation.fieldWithPath("memo").type(JsonFieldType.STRING).description("????????? ?????? ??????").optional(),
                                    PayloadDocumentation.fieldWithPath("lat").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("lng").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("????????? ??????"),
                                    PayloadDocumentation.fieldWithPath("order").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("category").type(JsonFieldType.STRING).description("?????? ????????????. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("googlePlaceId").type(JsonFieldType.STRING).description("Google Place API?????? ????????? ?????????")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("?????? ?????? API")
    class placeAdd {

        String endpoint = defaultEndpoint;

        @Test
        @DisplayName("given: ?????? ??????, ?????? ????????? ?????? ?????????, ?????? ?????? ????????? -> then: ????????? ?????? ????????? ??????")
        void success() throws Exception {
            //given
            PlaceAddRequest request = new PlaceAddRequest("newPlace123", "here is memo of place", 127.8997, 68.123123, "??????????????? XX??? YY??? ZZ-ZZZ", PlaceCategory.SPORT.name(), "c4 tg78q364grv7q2r3gfc27q3rg");
            Long meetingId = 333L;
            Long placeId = 453L;
            BDDMockito.given(addMeetingPlaceFacade.addMeetingPlace(BDDMockito.anyLong(), BDDMockito.any()))
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
                                    RequestDocumentation.parameterWithName("meetingId").description("????????? ??????????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("placeName").type(JsonFieldType.STRING).description("????????? ??????"),
                                    PayloadDocumentation.fieldWithPath("memo").type(JsonFieldType.STRING).description("????????? ?????? ??????").optional(),
                                    PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("????????? ??????"),
                                    PayloadDocumentation.fieldWithPath("lat").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("lng").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                    PayloadDocumentation.fieldWithPath("category").type(JsonFieldType.STRING).description("?????? ????????????. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("googlePlaceId").type(JsonFieldType.STRING).description("Google Place API?????? ????????? ?????????")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("meetingPlaceId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????????")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("?????? ?????? API")
    class placeModify {

        String endpoint = defaultEndpoint + "/{placeId}";

        @Test
        @DisplayName("given: ?????? ??????, ?????? ????????? ?????? ?????????, ????????? ?????? ?????? ????????? -> then: ?????? ?????? true")
        void success() throws Exception {
            //given
            PlaceModifyRequest request = new PlaceModifyRequest("modifyPlace123", "place memo modify", 128.0312, 67.00, "??????????????? YY??? ZZ??? XX-XXXX", PlaceCategory.ETC.name(), "c4 tg78q364grv7q2r3gfc27q3rg");
            Long meetingId = 333L;
            Long placeId = 453L;
            BDDMockito.willDoNothing().given(modifyMeetingPlaceFacade)
                    .modifyMeetingPlace(BDDMockito.anyLong(), BDDMockito.anyLong(), BDDMockito.any());

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
                                    RequestDocumentation.parameterWithName("meetingId").description("??????????????? ????????? ????????? ????????? ?????????"),
                                    RequestDocumentation.parameterWithName("placeId").description("????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("placeName").type(JsonFieldType.STRING).description("????????? ??????."),
                                    PayloadDocumentation.fieldWithPath("memo").type(JsonFieldType.STRING).description("????????? ?????? ??????. ???????????? ??? ????????? ???????????????.").optional(),
                                    PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("????????? ??????. ???????????? ??? ????????? ???????????????."),
                                    PayloadDocumentation.fieldWithPath("lat").type(JsonFieldType.NUMBER).description("????????? ?????????. ???????????? ??? ????????? ???????????????."),
                                    PayloadDocumentation.fieldWithPath("lng").type(JsonFieldType.NUMBER).description("????????? ?????????. ???????????? ??? ????????? ???????????????."),
                                    PayloadDocumentation.fieldWithPath("category").type(JsonFieldType.STRING).description("?????? ????????????. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("googlePlaceId").type(JsonFieldType.STRING).description("Google Place API?????? ????????? ?????????. ???????????? ??? ????????? ???????????????.")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ?????? ??????")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("?????? ?????? API")
    class placeRemove {

        String endpoint = defaultEndpoint + "/{placeId}";

        @Test
        @DisplayName("given: ?????? ??????, ?????? ????????? ?????? ?????????, ????????? ?????? ?????? ????????? -> then: ?????? ?????? true")
        void success() throws Exception {
            //given
            Long meetingId = 333L;
            Long placeId = 453L;
            BDDMockito.willDoNothing().given(removeMeetingPlaceFacade)
                    .removeMeetingPlace(BDDMockito.anyLong(), BDDMockito.anyLong());

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
                                    RequestDocumentation.parameterWithName("meetingId").description("??????????????? ????????? ????????? ????????? ?????????"),
                                    RequestDocumentation.parameterWithName("placeId").description("????????? ????????? ?????????")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("?????? ??????"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("?????? ??????"),
                                    PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ?????? ??????")
                            )
                    )
            );
        }
    }
}
