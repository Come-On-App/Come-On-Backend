package com.comeon.backend.api.support.utils;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.jwt.infrastructure.*;
import com.comeon.backend.meeting.presentation.interceptor.MemberAuthorizationInterceptor;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.config.web.log.LoggingManager;
import com.comeon.backend.meeting.infrastructure.interceptor.SecurityContextUserProvider;
import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberSimple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@Import({
        JwtManagerImpl.class,
        JwtBuilder.class,
        JwtParser.class,
        JwtProperties.class,
        JwtValidator.class,
        ReissueCondition.class,
        SecurityContextUserProvider.class,
        LoggingManager.class,
})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerUnitTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @SpyBean
    protected JwtManager jwtManager;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;

    @MockBean
    protected MeetingMemberDao meetingMemberDao;

    @MockBean
    MemberAuthorizationInterceptor memberAuthorizationInterceptor;

    protected Long currentUserId = 123L;
    protected Long currentMemberId = 489L;

    @BeforeEach
    void setUpUnitTest() {
        BDDMockito.given(memberAuthorizationInterceptor.preHandle(BDDMockito.any(), BDDMockito.any(), BDDMockito.any()))
                .willReturn(true);
        grantParticipant();
    }

    protected void grantHost() {
        BDDMockito.given(meetingMemberDao.findMemberSimple(BDDMockito.anyLong(), BDDMockito.anyLong()))
                .willReturn(new MemberSimple(currentUserId, currentMemberId, MemberRole.HOST));
    }

    protected void grantParticipant() {
        BDDMockito.given(meetingMemberDao.findMemberSimple(BDDMockito.anyLong(), BDDMockito.anyLong()))
                .willReturn(new MemberSimple(currentUserId, currentMemberId, MemberRole.PARTICIPANT));
    }

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
