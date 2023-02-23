package com.comeon.backend.api.meeting;

import com.comeon.backend.api.support.utils.RestDocsTestSupport;
import com.comeon.backend.api.support.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.api.MeetingController;
import com.comeon.backend.meeting.command.application.CreateMeetingFacade;
import com.comeon.backend.meeting.command.application.ModifyMeetingFacade;
import com.comeon.backend.meeting.command.application.dto.MeetingCreateRequest;
import com.comeon.backend.meeting.command.application.dto.MeetingModifyRequest;
import com.comeon.backend.meeting.query.application.MeetingQueryService;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import com.comeon.backend.meeting.query.dto.MeetingMetaData;
import com.comeon.backend.meeting.query.dto.MeetingSimple;
import com.comeon.backend.meeting.command.domain.PlaceCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebMvcTest({MeetingController.class})
public class MeetingControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingDao meetingDao;

    @MockBean
    MeetingQueryService meetingQueryService;

    @MockBean
    CreateMeetingFacade createMeetingFacade;

    @MockBean
    ModifyMeetingFacade modifyMeetingFacade;

    @Nested
    @DisplayName("모임 등록 API")
    class meetingAdd {

        String endpoint = "/api/v1/meetings";

        @Test
        @DisplayName("given: 인증 필요, 모임 이름, 모임 썸네일 이미지, 달력 시작일, 달력 종료일 -> then: HTTP 200, 생성된 모임 식별값 반환")
        void success() throws Exception {
            //given
            MeetingCreateRequest request = new MeetingCreateRequest(
                    "Come-On 모임",
                    "https://xxx.xxxxx.xxx/meeting-thumbnail-image-url",
                    LocalDate.of(2023, 1, 20),
                    LocalDate.of(2023, 2, 28)
            );

            long meetingId = 28L;
            BDDMockito.given(createMeetingFacade.createMeeting(BDDMockito.anyLong(), BDDMockito.any()))
                    .willReturn(meetingId);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint)
                            .content(createJson(request))
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingId").value(meetingId));

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingName").type(JsonFieldType.STRING).description("모임의 이름."),
                                    PayloadDocumentation.fieldWithPath("meetingImageUrl").type(JsonFieldType.STRING).description("모임 리스트에 표시될 모임 대표 썸네일 이미지."),
                                    PayloadDocumentation.fieldWithPath("calendarStartFrom").type(JsonFieldType.STRING).description("모임 일자 투표가 가능한 캘린더의 시작일. +\nyyyy-MM-dd 형식의 날짜 지정. +\nex) 2023-01-01"),
                                    PayloadDocumentation.fieldWithPath("calendarEndTo").type(JsonFieldType.STRING).description("모임 일자 투표가 가능한 캘린더의 종료일. +\nyyyy-MM-dd 형식으로 날짜 지정. +\nex) 2023-01-01")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("생성된 모임의 식별값")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임 정보 수정 API")
    class meetingModify {

        String endpoint = "/api/v1/meetings/{meeting-id}";

        @Test
        @DisplayName("given: 인증 필요, 모임 정보 -> then: HTTP 200")
        void success() throws Exception {
            //given
            MeetingModifyRequest request = new MeetingModifyRequest(
                    "수정된 Come-On 모임",
                    "https://xxx.xxxxx.xxx/meeting-thumbnail-image-url",
                    null,
                    LocalDate.of(2023, 2, 28)
            );

            BDDMockito.willDoNothing().given(modifyMeetingFacade)
                    .modifyMeetingInfo(BDDMockito.anyLong(), BDDMockito.any());

            long meetingId = 28L;

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.patch(endpoint, meetingId)
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
                                    RequestDocumentation.parameterWithName("meeting-id").description("수정할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingName").type(JsonFieldType.STRING).description("수정할 모임의 이름.").optional(),
                                    PayloadDocumentation.fieldWithPath("meetingImageUrl").type(JsonFieldType.STRING).description("수정할 모임 대표 썸네일 이미지.").optional(),
                                    PayloadDocumentation.fieldWithPath("calendarStartFrom").type(JsonFieldType.STRING).description("수정할 캘린더의 시작일. +\nyyyy-MM-dd 형식의 날짜 지정. +\nex) 2023-01-01").optional(),
                                    PayloadDocumentation.fieldWithPath("calendarEndTo").type(JsonFieldType.STRING).description("수정할 캘린더의 종료일. +\nyyyy-MM-dd 형식으로 날짜 지정. +\nex) 2023-01-01").optional()
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
    @DisplayName("모임 리스트 조회 API")
    class meetingList {

        String endpoint = "/api/v1/meetings";

        @Test
        @DisplayName("given: 인증 필요, (optional) 모임이름, 검색 시작일, 검색 종료일, 페이지 정보 -> then: HTTP 200, 본인이 속한 모임 리스트 정보 반환")
        void success() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("size", String.valueOf(3));
            params.add("page", String.valueOf(0));
            params.add("searchWords", "ex");
            LocalDate dateFrom = LocalDate.of(2023, 3, 01);
            params.add("dateFrom", dateFrom.format(DateTimeFormatter.ISO_DATE));
            LocalDate dateTo = LocalDate.of(2023, 3, 31);
            params.add("dateTo", dateTo.format(DateTimeFormatter.ISO_DATE));

            // mocking
            List<MeetingSimple> sliceResults = List.of(
                    new MeetingSimple(10L, new MeetingSimple.HostUserSimple(31L, "user31", null), 10, MemberRole.PARTICIPANT.name(), "ex meeting 10", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 23), LocalTime.of(8, 0, 0), "https://xxxx.xxxxxxx.xxxx/xxxxxxxxxxx", null),
                    new MeetingSimple(14L, new MeetingSimple.HostUserSimple(currentRequestATK.getPayload().getUserId(), currentRequestATK.getPayload().getNickname(), "https://xxx.xxxx.xxxxxx/xxxxxx"), 4, MemberRole.HOST.name(), "meeting 14 ex", LocalDate.of(2023, 3, 10), LocalDate.of(2023, 3, 18), LocalTime.of(8, 0, 0), "https://xxxx.xxxxxxx.xxxx/xxxxxxxxxxx", null),
                    new MeetingSimple(23L, new MeetingSimple.HostUserSimple(11L, "user 11", null), 6, MemberRole.PARTICIPANT.name(), "meeting ex 23", LocalDate.of(2023, 3, 22), LocalDate.of(2023, 3, 30), LocalTime.of(12, 0, 0), "https://xxxx.xxxxxxx.xxxx/xxxxxxxxx", new MeetingSimple.ConfirmedDate(LocalDate.of(2023, 3, 5), LocalDate.of(2023, 3, 5)))
            );
            BDDMockito.given(meetingDao.findMeetingSlice(BDDMockito.anyLong(), BDDMockito.any(), BDDMockito.any()))
                    .willReturn(new SliceImpl<>(sliceResults, Pageable.ofSize(3), true));

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get(endpoint)
                            .params(params)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk());

            // docs
            perform.andDo(
                    restDocs.document(
                            RequestDocumentation.requestParameters(
                                    getTitleAttributes("요청 파라미터"),
                                    RequestDocumentation.parameterWithName("size").description("한 페이지당 최대 조회할 요소 개수. 기본값은 10 입니다.").optional(),
                                    RequestDocumentation.parameterWithName("page").description("조회할 페이지 번호. 0부터 시작합니다. 기본값은 0 입니다.").optional(),
                                    RequestDocumentation.parameterWithName("searchWords").description("공백으로 구분된 검색할 단어들. +\n해당 필드에 주어진 데이터를 공백으로 구분하여, 모임 이름에 해당 단어가 포함되어 있으면 검색 결과에 포함합니다. +\n입력하지 않으면 전체 모임을 조회합니다.").optional(),
                                    RequestDocumentation.parameterWithName("dateFrom").description("검색 시작일. 해당 필드를 입력하면 모임별 모임일 투표 캘린더의 시작일자가 dateFrom 파라미터에 입력한 일자보다 같거나 큰 모임만 조회합니다. +\nyyyy-MM-dd 형식").optional(),
                                    RequestDocumentation.parameterWithName("dateTo").description("검색 종료일. 해당 필드를 입력하면 모임별 모임일 투표 캘린더의 종료일자가 dateTo 파라미터에 입력한 일자보다 같거나 작은 모임만 조회합니다. +\nyyyy-MM-dd 형식").optional()
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("contents").withSubsectionId("slice-contents"),
                                    getTitleAttributes("contents 응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("모임의 식별값"),
                                    PayloadDocumentation.subsectionWithPath("hostUser").type(JsonFieldType.OBJECT).description("해당 모임의 방장 정보"),
                                    PayloadDocumentation.fieldWithPath("hostUser.userId").type(JsonFieldType.NUMBER).description("방장의 유저 식별값"),
                                    PayloadDocumentation.fieldWithPath("hostUser.nickname").type(JsonFieldType.STRING).description("방장의 닉네임"),
                                    PayloadDocumentation.fieldWithPath("hostUser.profileImageUrl").type(JsonFieldType.STRING).description("방장의 프로필 이미지 URL").optional(),
                                    PayloadDocumentation.fieldWithPath("memberCount").type(JsonFieldType.NUMBER).description("해당 모임의 전체 참여자 숫자"),
                                    PayloadDocumentation.fieldWithPath("myMeetingRole").type(JsonFieldType.STRING).description("해당 모임에서 현재 유저의 권한 +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE)),
                                    PayloadDocumentation.fieldWithPath("meetingName").type(JsonFieldType.STRING).description("모임의 이름"),
                                    PayloadDocumentation.fieldWithPath("calendarStartFrom").type(JsonFieldType.STRING).description("모임일 투표 캘린더의 시작 일자"),
                                    PayloadDocumentation.fieldWithPath("calendarEndTo").type(JsonFieldType.STRING).description("모임일 투표 캘린더의 종료(마지막) 일자"),
                                    PayloadDocumentation.fieldWithPath("meetingStartTime").type(JsonFieldType.STRING).description("모임 시작 시간. +\nHH:mm:ss 형식"),
                                    PayloadDocumentation.fieldWithPath("meetingImageUrl").type(JsonFieldType.STRING).description("모임의 썸네일 이미지 URL"),
                                    PayloadDocumentation.subsectionWithPath("fixedDate").type(JsonFieldType.OBJECT).description("확정된 모임일 정보").optional(),
                                    PayloadDocumentation.fieldWithPath("fixedDate.startFrom").type(JsonFieldType.STRING).description("모임 시작일. +\nYYYY-MM-DD 형식."),
                                    PayloadDocumentation.fieldWithPath("fixedDate.endTo").type(JsonFieldType.STRING).description("모임 종료일. +\nYYYY-MM-DD 형식")
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임 상세 조회 API")
    class meetingDetails {

        String endpoint = "/api/v1/meetings/{meeting-id}";

        @Test
        @DisplayName("given: 인증 필요, 경로 변수에 모임 식별값 -> then: HTTP 200, 주어진 모임 식별값의 모임 상세 응답")
        void success() throws Exception {
            //given
            Long mockMeetingId = 333L;

            // mocking
            MeetingDetails response = new MeetingDetails(
                    new MeetingMetaData(
                            mockMeetingId,
                            "https://xxxx.xxxx.xxxx/xxxxxx",
                            "예제 모임 1",
                            LocalTime.of(11,0,0),
                            new MeetingMetaData.HostUserSimple(112L, "user112", "https://xxx.xxx.xxxx/xxxxx"),
                            new MeetingMetaData.MeetingCalendar(
                                    LocalDate.of(2023, 3,1),
                                    LocalDate.of(2023, 3,31)
                            ),
                            new MeetingMetaData.ConfirmedDate(
                                    LocalDate.of(2023, 3, 11),
                                    LocalDate.of(2023, 3, 11)
                            )
                    ),
                    List.of(
                            new MeetingDetails.MemberDetails(88L, 112L, "user112", "https://xxx.xxx.xxxx/xxxxx", MemberRole.HOST.name()),
                            new MeetingDetails.MemberDetails(109L, 134L, "user134", null, MemberRole.PARTICIPANT.name())
                    ),
                    List.of(
                            new MeetingDetails.DateVotingSimple(LocalDate.of(2023, 3, 5), 2, true),
                            new MeetingDetails.DateVotingSimple(LocalDate.of(2023, 3, 7), 1, false),
                            new MeetingDetails.DateVotingSimple(LocalDate.of(2023, 3, 11), 1, true)
                    ),
                    List.of(
                            new MeetingDetails.PlaceDetails(3323L, "홍대역", "여기서 모이자", 68.123, 127.31561, "XXX-YYYY", 1, PlaceCategory.ETC.name(), "asd23234tabn4tav"),
                            new MeetingDetails.PlaceDetails(3642L, "홍대 마약 떡볶이", "점심식사", 68.4567, 127.1252346, "ZZZ-1231", 2, PlaceCategory.RESTAURANT.name(), "36m3w546hwh3w4gv")
                    )
            );
            BDDMockito.given(meetingQueryService.findMeetingDetails(BDDMockito.anyLong(), BDDMockito.anyLong()))
                    .willReturn(response);

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
                                    RequestDocumentation.parameterWithName("meeting-id").description("조회할 모임의 식별값")
                            ),
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("meetingMetaData").type(JsonFieldType.OBJECT).description("모임의 기본 정보."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.meetingId").type(JsonFieldType.NUMBER).description("모임의 식별값."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.thumbnailImageUrl").type(JsonFieldType.STRING).description("모임 썸네일 이미지."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.meetingName").type(JsonFieldType.STRING).description("모임 이름."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.meetingStartTime").type(JsonFieldType.STRING).description("모임 만남 시간. +\nHH:mm:ss 형식"),

                                    PayloadDocumentation.subsectionWithPath("meetingMetaData.hostUser").type(JsonFieldType.OBJECT).description("해당 모임의 방장 정보."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.hostUser.userId").type(JsonFieldType.NUMBER).description("방장의 유저 식별값."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.hostUser.nickname").type(JsonFieldType.STRING).description("방장의 닉네임."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.hostUser.profileImageUrl").type(JsonFieldType.STRING).description("방장의 프로필 이미지 URL.").optional(),

                                    PayloadDocumentation.subsectionWithPath("meetingMetaData.calendar").type(JsonFieldType.OBJECT).description("모임 일자 투표가 가능한 캘린더 정보."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.calendar.startFrom").type(JsonFieldType.STRING).description("모임 일자 투표가 가능한 캘린더의 시작 일자. +\nYYYY-MM-DD 형식."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.calendar.endTo").type(JsonFieldType.STRING).description("모임 일자 투표가 가능한 캘린더의 마지막 일자. +\nYYYY-MM-DD 형식"),

                                    PayloadDocumentation.subsectionWithPath("meetingMetaData.fixedDate").type(JsonFieldType.OBJECT).description("모임 확정 일자 정보.").optional(),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.fixedDate.startFrom").type(JsonFieldType.STRING).description("확정된 모임 일자의 시작일. +\nYYYY-MM-DD 형식."),
                                    PayloadDocumentation.fieldWithPath("meetingMetaData.fixedDate.endTo").type(JsonFieldType.STRING).description("확정된 모임 일자의 종료일. +\nYYYY-MM-DD 형식"),

                                    PayloadDocumentation.subsectionWithPath("members").type(JsonFieldType.ARRAY).description("모임에 가입된 회원 정보 리스트."),
                                    PayloadDocumentation.fieldWithPath("members[].memberId").type(JsonFieldType.NUMBER).description("회원의 모임 회원 번호. +\n유저 식별값과는 별개의 정보입니다."),
                                    PayloadDocumentation.fieldWithPath("members[].userId").type(JsonFieldType.NUMBER).description("회원의 유저 식별값."),
                                    PayloadDocumentation.fieldWithPath("members[].nickname").type(JsonFieldType.STRING).description("회원의 유저 닉네임."),
                                    PayloadDocumentation.fieldWithPath("members[].profileImageUrl").type(JsonFieldType.STRING).description("회원의 유저 프로필 이미지 URL.").optional(),
                                    PayloadDocumentation.fieldWithPath("members[].memberRole").type(JsonFieldType.STRING).description("모임에서 해당 회원의 권한. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE)),

                                    PayloadDocumentation.subsectionWithPath("votingDates").type(JsonFieldType.ARRAY).description("모임일 투표 현황 정보 리스트."),
                                    PayloadDocumentation.fieldWithPath("votingDates[].date").type(JsonFieldType.STRING).description("모임일 투표 일자. +\nYYYY-MM-DD 형식."),
                                    PayloadDocumentation.fieldWithPath("votingDates[].memberCount").type(JsonFieldType.NUMBER).description("해당 일자에 투표한 회원 수."),
                                    PayloadDocumentation.fieldWithPath("votingDates[].myVoting").type(JsonFieldType.BOOLEAN).description("현재 유저가 해당 일자에 투표 했는지 여부."),

                                    PayloadDocumentation.subsectionWithPath("places").type(JsonFieldType.ARRAY).description("모임에 등록된 장소 리스트").optional(),
                                    PayloadDocumentation.fieldWithPath("places[].meetingPlaceId").type(JsonFieldType.NUMBER).description("등록된 장소의 식별값"),
                                    PayloadDocumentation.fieldWithPath("places[].placeName").type(JsonFieldType.STRING).description("장소의 이름"),
                                    PayloadDocumentation.fieldWithPath("places[].memo").type(JsonFieldType.STRING).description("장소에 대한 메모").optional(),
                                    PayloadDocumentation.fieldWithPath("places[].lat").type(JsonFieldType.NUMBER).description("장소의 위도값").optional(),
                                    PayloadDocumentation.fieldWithPath("places[].lng").type(JsonFieldType.NUMBER).description("장소의 경도값").optional(),
                                    PayloadDocumentation.fieldWithPath("places[].address").type(JsonFieldType.STRING).description("장소의 주소").optional(),
                                    PayloadDocumentation.fieldWithPath("places[].order").type(JsonFieldType.NUMBER).description("장소 정렬 순서"),
                                    PayloadDocumentation.fieldWithPath("places[].category").type(JsonFieldType.STRING).description("장소 카테고리. +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.PLACE_CATEGORY_CODE)),
                                    PayloadDocumentation.fieldWithPath("places[].googlePlaceId").type(JsonFieldType.STRING).description("Google Place API에서 장소의 식별값").optional()
                            )
                    )
            );
        }
    }
}
