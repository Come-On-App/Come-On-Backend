package com.comeon.backend.api.support.utils;

import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.config.security.JwtAccessDeniedHandler;
import com.comeon.backend.config.security.JwtAuthenticationEntryPoint;
import com.comeon.backend.config.security.JwtAuthenticationFilter;
import com.comeon.backend.config.security.JwtAuthenticationProvider;
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
    void setUpSupport() {
        currentRequestATK = jwtManager.buildAtk(currentUserId, "user_" + currentUserId, Role.USER.getValue());
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
            Attributes.attributes(Attributes.key("title").value("?????? ?????? ??????")),
            PayloadDocumentation.fieldWithPath("errorCode").type(JsonFieldType.NUMBER).description("?????? ??????"),
            PayloadDocumentation.fieldWithPath("errorDescription").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????? ???????????? ?????? ?????????"),
            PayloadDocumentation.subsectionWithPath("errors").type(JsonFieldType.ARRAY).description("?????? ?????? ????????? ?????? ????????? ?????? ?????? ???????????? ?????? List").optional()
    );

    protected ResponseFieldsSnippet errorResponseDotErrorsSnippet = PayloadDocumentation.responseFields(
            PayloadDocumentation.beneathPath("errors").withSubsectionId("errors"),
            Attributes.attributes(Attributes.key("title").value("errors ??? ??????")),
            PayloadDocumentation.fieldWithPath("field").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????????").optional(),
            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????? ?????? ?????????").optional(),
            PayloadDocumentation.fieldWithPath("rejectedValue").type(JsonFieldType.VARIES).description("?????? ????????? ????????? ???").optional()
    );

    protected Snippet[] validErrorSnippets = {errorResponseSnippet, errorResponseDotErrorsSnippet};

    protected HeaderDescriptor authorizationHeaderDescriptor = HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).description("????????? ????????? ??????. ?????? ?????? 'Bearer '??? ???????????? ?????????. +\nex) Bearer {access-token} +\n????????? ????????? ?????? ????????? ??????????????????.");

    @NotNull
    protected Map<String, Object> getTitleAttributes(String titleValue) {
        return Attributes.attributes(Attributes.key("title").value(titleValue));
    }
}
