package com.comeon.backend.api.error;

import com.comeon.backend.api.error.controller.ErrorRestDocsController;
import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.api.utils.RestDocsUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@WebMvcTest(ErrorRestDocsController.class)
public class ErrorResponseRestDocsTest extends RestDocsTestSupport {

    @Nested
    @DisplayName("API 오류 응답")
    class errorResponse {
        @Test
        @DisplayName("일반 오류")
        void normalError() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/response")
                            .accept(MediaType.APPLICATION_JSON)
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(restDocs.document(errorResponseSnippet));
        }

        @Test
        @DisplayName("API 오류 응답 - 검증 오류")
        void validError() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/valid-error")
                            .accept(MediaType.APPLICATION_JSON)
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(restDocs.document(validErrorSnippets));
        }
    }



    @Nested
    @DisplayName("오류 코드")
    class errorCode {
        @Test
        @DisplayName("공통 오류 코드")
        void common() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/code/common")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<Integer, String> response = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse()
                            .getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(
                            restDocs.document(
                                    RestDocsUtil.customResponseFields(
                                            "error-code-response",
                                            Attributes.attributes(Attributes.key("title").value("공통 오류 코드")),
                                            enumConvertFieldDescriptor(response)
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("유저 오류 코드")
        void users() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/code/users")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<Integer, String> response = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse()
                            .getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(
                            restDocs.document(
                                    RestDocsUtil.customResponseFields(
                                            "error-code-response",
                                            Attributes.attributes(Attributes.key("title").value("유저 오류 코드")),
                                            enumConvertFieldDescriptor(response)
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("이미지 오류 코드")
        void image() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/code/image")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<Integer, String> response = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse()
                            .getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(
                            restDocs.document(
                                    RestDocsUtil.customResponseFields(
                                            "error-code-response",
                                            Attributes.attributes(Attributes.key("title").value("이미지 오류 코드")),
                                            enumConvertFieldDescriptor(response)
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("모임 오류 코드")
        void meetings() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/code/meetings")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<Integer, String> response = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse()
                            .getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(
                            restDocs.document(
                                    RestDocsUtil.customResponseFields(
                                            "error-code-response",
                                            Attributes.attributes(Attributes.key("title").value("모임 오류 코드")),
                                            enumConvertFieldDescriptor(response)
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("JWT 오류 코드")
        void jwt() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/error/code/jwt")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<Integer, String> response = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse()
                            .getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(
                            restDocs.document(
                                    RestDocsUtil.customResponseFields(
                                            "error-code-response",
                                            Attributes.attributes(Attributes.key("title").value("인증/인가 오류 코드")),
                                            enumConvertFieldDescriptor(response)
                                    )
                            )
                    );
        }
    }

    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<Integer, String> enumValues) {
        return enumValues.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(x -> fieldWithPath(String.valueOf(x.getKey())).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }
}
