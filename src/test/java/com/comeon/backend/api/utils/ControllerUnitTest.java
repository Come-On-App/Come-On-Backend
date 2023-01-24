package com.comeon.backend.api.utils;

import com.comeon.backend.jwt.application.JwtManager;
import com.comeon.backend.jwt.domain.RefreshTokenRepository;
import com.comeon.backend.jwt.infra.*;
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
        ReissueConditionImpl.class
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
    RefreshTokenRepository refreshTokenRepository;

    protected Long currentUserId = 123L;

    @BeforeEach
    void setUp() {
        BDDMockito.given(refreshTokenRepository.findUserIdBy(BDDMockito.anyString()))
                .willReturn(Optional.of(currentUserId));
    }

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
