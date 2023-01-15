package com.comeon.backend.api.utils;

import com.comeon.backend.common.jwt.*;
import com.comeon.backend.common.security.JwtAccessDeniedHandler;
import com.comeon.backend.common.security.JwtAuthenticationEntryPoint;
import com.comeon.backend.common.security.JwtAuthenticationFilter;
import com.comeon.backend.common.security.JwtAuthenticationProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@Import({
        JwtParser.class,
        JwtGenerator.class,
        JwtValidator.class,
})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerUnitTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtGenerator jwtGenerator;

    @Autowired
    protected JwtParser jwtParser;

    @Autowired
    protected JwtValidator jwtValidator;

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
