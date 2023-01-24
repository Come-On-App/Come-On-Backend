package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.api.utils.RestDocsUtil;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.command.application.MeetingMemberFacade;
import com.comeon.backend.meeting.command.application.dto.MeetingMemberSummary;
import com.comeon.backend.meeting.command.domain.MeetingMemberRole;
import com.comeon.backend.meeting.presentation.MeetingApiController;
import com.comeon.backend.meeting.presentation.request.MeetingAddRequest;
import com.comeon.backend.meeting.presentation.request.MeetingJoinRequest;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.result.FindMeetingSliceResult;
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
import java.util.List;

@WebMvcTest({MeetingApiController.class})
public class MeetingApiControllerTest extends RestDocsTestSupport {

    @MockBean
    MeetingFacade meetingFacade;

    @MockBean
    MeetingMemberFacade meetingMemberFacade;

    @MockBean
    MeetingDao meetingDao;

    @Nested
    @DisplayName("모임 등록 API")
    class meetingAdd {

        String endpoint = "/api/v1/meetings";

        @Test
        @DisplayName("given: 인증 필요, 모임 이름, 모임 썸네일 이미지, 달력 시작일, 달력 종료일 -> then: HTTP 200, 생성된 모임 식별값 반환")
        void success() throws Exception {
            //given
            MeetingAddRequest request = new MeetingAddRequest(
                    "Come-On 모임",
                    "https://xxx.xxxxx.xxx/meeting-thumbnail-image-url",
                    LocalDate.of(2023, 1, 20),
                    LocalDate.of(2023,2,28)
            );

            long meetingId = 28L;
            BDDMockito.given(meetingFacade.addMeeting(BDDMockito.anyLong(), BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.any(), BDDMockito.any()))
                    .willReturn(meetingId);

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint)
                            .content(createJson(request))
                            .header(HttpHeaders.AUTHORIZATION, currentRequestATK.getToken())
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
    @DisplayName("모임 참가 API")
    class meetingJoin {

        String endpoint = "/api/v1/meetings/join";

        @Test
        @DisplayName("given: 인증 필요, 유효한 모임 코드 -> then: HTTP 200, meetingId, meetingMember(memberId, role)")
        void join() throws Exception {
            //given
            MeetingJoinRequest request = new MeetingJoinRequest("EF25FK");

            // mocking
            long meetingIdMock = 300L;
            long memberIdMock = 4777L;
            String memberRoleMock = MeetingMemberRole.PARTICIPANT.name();
            BDDMockito.given(meetingMemberFacade.joinMeeting(BDDMockito.anyLong(), BDDMockito.anyString()))
                    .willReturn(
                            new MeetingMemberSummary(meetingIdMock, memberIdMock, memberRoleMock)
                    );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.post(endpoint)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .content(createJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingId").value(meetingIdMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember.memberId").value(memberIdMock))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.meetingMember.role").value(memberRoleMock));

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            PayloadDocumentation.requestFields(
                                    getTitleAttributes("요청 필드"),
                                    PayloadDocumentation.fieldWithPath("entryCode").type(JsonFieldType.STRING).description("모임 입장 코드")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.fieldWithPath("meetingId").type(JsonFieldType.NUMBER).description("가입한 모임의 식별값"),
                                    PayloadDocumentation.subsectionWithPath("meetingMember").type(JsonFieldType.OBJECT).description("가입된 모임 회원 정보")
                            ),
                            PayloadDocumentation.responseFields(
                                    PayloadDocumentation.beneathPath("meetingMember").withSubsectionId("MEETING-MEMBER"),
                                    getTitleAttributes("meetingMember의 각 필드"),
                                    PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("해당 모임에서의 회원 번호. 유저 식별값인 user-id와 회원 번호인 member-id는 서로 다른 값 입니다."),
                                    PayloadDocumentation.fieldWithPath("role").type(JsonFieldType.STRING).description("해당 모임에서 유저의 권한 +\n" + RestDocsUtil.generateLinkCode(RestDocsUtil.DocUrl.MEETING_MEMBER_ROLE))
                            )
                    )
            );
        }
    }

    @Nested
    @DisplayName("모임 리스트 조회 API")
    class meetingSummaryList {

        String endpoint = "/api/v1/meetings";

        @Test
        @DisplayName("given: 인증 필요, (optional) 모임이름, 검색 시작일, 검색 종료일, 페이지 정보 -> then: HTTP 200, 본인이 속한 모임 리스트 정보 반환")
        void success() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("size", String.valueOf(3));
            params.add("page", String.valueOf(0));
            params.add("searchWords", "ex");
            params.add("dateFrom", "2023-03-01");
            params.add("dateTo", "2023-03-31");

            // mocking
            List<FindMeetingSliceResult> sliceResults = List.of(
                    new FindMeetingSliceResult(10L, 31L, "user31", null, 10, MeetingMemberRole.PARTICIPANT.name(), "ex meeting 10", "2023-03-01", "2023-03-23", "https://xxxx.xxxxxxx.xxxx/xxxxxxxxxxx"),
                    new FindMeetingSliceResult(14L, currentRequestATK.getPayload().getUserId(), currentRequestATK.getPayload().getNickname(), "https://xxx.xxxx.xxxxxx/xxxxxx", 4, MeetingMemberRole.HOST.name(), "meeting 14 ex", "2023-03-10", "2023-03-18", "https://xxxx.xxxxxxx.xxxx/xxxxxxxxx"),
                    new FindMeetingSliceResult(23L, 11L, "user 11", null, 6, MeetingMemberRole.PARTICIPANT.name(), "meeting ex 23", "2023-03-22", "2023-03-30", "https://xxxx.xxxxxxx.xxxx/xxxxxxxxx")
            );
            BDDMockito.given(meetingDao.findMeetingSlice(BDDMockito.anyLong(), BDDMockito.any(), BDDMockito.any()))
                    .willReturn(
                            new SliceImpl<>(
                                    sliceResults,
                                    Pageable.ofSize(3),
                                    true
                            )
                    );

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
                                    PayloadDocumentation.fieldWithPath("meetingImageUrl").type(JsonFieldType.STRING).description("모임의 썸네일 이미지 URL"),
                                    PayloadDocumentation.fieldWithPath("fixedDates").type(JsonFieldType.NULL).description("임시"),
                                    PayloadDocumentation.fieldWithPath("meetingStatus").type(JsonFieldType.NULL).description("임시")
                            )
                    )
            );
        }
    }
}
