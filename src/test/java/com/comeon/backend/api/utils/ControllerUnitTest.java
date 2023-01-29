package com.comeon.backend.api.utils;

import com.comeon.backend.common.config.interceptor.MemberRole;
import com.comeon.backend.jwt.application.JwtManager;
import com.comeon.backend.jwt.domain.RefreshTokenRepository;
import com.comeon.backend.jwt.infra.*;
import com.comeon.backend.common.config.interceptor.SecurityContextUserProvider;
import com.comeon.backend.common.config.interceptor.MeetingMemberDao;
import com.comeon.backend.common.config.interceptor.MemberSimpleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@ActiveProfiles("test")
@Import({
        JwtManager.class,
        JwtBuilderImpl.class,
        JwtParserImpl.class,
        JwtProperties.class,
        JwtValidatorImpl.class,
        ReissueConditionImpl.class,
        SecurityContextUserProvider.class
})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerUnitTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtManager jwtManager;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;

    @MockBean
    protected MeetingMemberDao meetingMemberDao;

    protected Long currentUserId = 123L;
    protected Long currentMemberId = 489L;

    @BeforeEach
    void setUpUnitTest() {
        BDDMockito.given(refreshTokenRepository.findUserIdBy(BDDMockito.anyString()))
                .willReturn(Optional.of(currentUserId));
        grantParticipant();
    }

    protected void grantHost() {
        BDDMockito.given(meetingMemberDao.findMemberSimple(BDDMockito.anyLong(), BDDMockito.anyLong()))
                .willReturn(new MemberSimpleResponse(currentUserId, currentMemberId, MemberRole.HOST));
    }

    protected void grantParticipant() {
        BDDMockito.given(meetingMemberDao.findMemberSimple(BDDMockito.anyLong(), BDDMockito.anyLong()))
                .willReturn(new MemberSimpleResponse(currentUserId, currentMemberId, MemberRole.PARTICIPANT));
    }

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
