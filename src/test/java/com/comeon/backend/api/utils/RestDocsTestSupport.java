package com.comeon.backend.api.utils;

import com.comeon.backend.common.jwt.JwtGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureMockMvc
@Import({
        RestDocsConfig.class,
        JwtGenerator.class
})
@ExtendWith(RestDocumentationExtension.class)
public class RestDocsTestSupport extends ControllerUnitTest {

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected JwtGenerator jwtGenerator;

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


}
