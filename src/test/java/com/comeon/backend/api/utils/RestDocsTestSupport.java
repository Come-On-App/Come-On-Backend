package com.comeon.backend.api.utils;

import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.common.security.JwtAccessDeniedHandler;
import com.comeon.backend.common.security.JwtAuthenticationEntryPoint;
import com.comeon.backend.common.security.JwtAuthenticationFilter;
import com.comeon.backend.common.security.JwtAuthenticationProvider;
import com.comeon.backend.user.command.domain.Role;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

@AutoConfigureMockMvc
@Import({
        RestDocsConfig.class,
        JwtAuthenticationFilter.class,
        JwtAuthenticationProvider.class,
        JwtAccessDeniedHandler.class,
        JwtAuthenticationEntryPoint.class
})
@ExtendWith(RestDocumentationExtension.class)
public class RestDocsTestSupport extends ControllerUnitTest {

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected JwtAuthenticationProvider jwtAuthenticationProvider;

    protected JwtToken currentRequestATK;

    @BeforeEach
    void setUp() {
        currentRequestATK = jwtGenerator.initBuilder(TokenType.ACCESS)
                .userId(123L)
                .nickname("user_123")
                .authorities(Role.USER.getValue())
                .build();
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationProvider.getAuthentication(currentRequestATK.getToken()));
    }

    @BeforeEach
    void setUp(final WebApplicationContext context,
               final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    protected ResponseFieldsSnippet errorResponseSnippet = PayloadDocumentation.responseFields(
            Attributes.attributes(Attributes.key("title").value("오류 응답 필드")),
            PayloadDocumentation.fieldWithPath("errorCode").type(JsonFieldType.NUMBER).description("오류 코드"),
            PayloadDocumentation.fieldWithPath("errorDescription").type(JsonFieldType.STRING).description("요청 처리 실패 원인을 나타내는 오류 메시지"),
            PayloadDocumentation.subsectionWithPath("errors").type(JsonFieldType.ARRAY).description("오류 발생 필드와 해당 필드에 대한 오류 메시지를 담은 List").optional()
    );

    protected ResponseFieldsSnippet errorResponseDotErrorsSnippet = PayloadDocumentation.responseFields(
            PayloadDocumentation.beneathPath("errors").withSubsectionId("errors"),
            Attributes.attributes(Attributes.key("title").value("errors 각 필드")),
            PayloadDocumentation.fieldWithPath("field").type(JsonFieldType.STRING).description("검증 오류가 발생한 필드명").optional(),
            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("해당 필드에 발생한 검증 오류 메시지").optional(),
            PayloadDocumentation.fieldWithPath("rejectedValue").type(JsonFieldType.VARIES).description("해당 필드에 입력된 값").optional()
    );

    protected Snippet[] validErrorSnippets = {errorResponseSnippet, errorResponseDotErrorsSnippet};

    protected HeaderDescriptor authorizationHeaderDescriptor = HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).description("유저의 엑세스 토큰. 토큰 앞에 'Bearer '를 붙여줘야 합니다. +\nex) Bearer {access-token} +\n자세한 내용은 요청 예시를 참고해주세요.");

    @NotNull
    protected Map<String, Object> getTitleAttributes(String titleValue) {
        return Attributes.attributes(Attributes.key("title").value(titleValue));
    }
}
