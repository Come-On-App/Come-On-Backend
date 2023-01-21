package com.comeon.backend.api.enums;

import com.comeon.backend.api.enums.controller.EnumRestDocsController;
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
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

@WebMvcTest(EnumRestDocsController.class)
public class EnumRestDocsTest extends RestDocsTestSupport {

    @Nested
    @DisplayName("enum rest docs")
    class enums {

        @Test
        @DisplayName("oauthProvider codes")
        void oauthProvider() throws Exception {
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/enums/oauth-provider")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<String, String> data = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse().getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            perform.andDo(
                    restDocs.document(
                            RestDocsUtil.customResponseFields(
                                    "enum-response",
                                    getTitleAttributes("소셜 로그인 서비스 제공 벤더 코드"),
                                    enumConvertFieldDescriptor(data)
                            )
                    )
            );
        }

        @Test
        @DisplayName("user role codes")
        void userRole() throws Exception {
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/enums/user-role")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<String, String> data = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse().getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            perform.andDo(
                    restDocs.document(
                            RestDocsUtil.customResponseFields(
                                    "enum-response",
                                    getTitleAttributes("유저 권한 코드"),
                                    enumConvertFieldDescriptor(data)
                            )
                    )
            );
        }

        @Test
        @DisplayName("user status codes")
        void userStatus() throws Exception {
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/enums/user-status")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<String, String> data = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse().getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            perform.andDo(
                    restDocs.document(
                            RestDocsUtil.customResponseFields(
                                    "enum-response",
                                    getTitleAttributes("유저 상태 코드"),
                                    enumConvertFieldDescriptor(data)
                            )
                    )
            );
        }

        @Test
        @DisplayName("meeting member role codes")
        void meetingMemberRole() throws Exception {
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/enums/meeting-member-role")
                            .accept(MediaType.APPLICATION_JSON)
            );

            Map<String, String> data = objectMapper.readValue(
                    perform.andReturn()
                            .getResponse().getContentAsByteArray(),
                    new TypeReference<>() {
                    }
            );

            perform.andDo(
                    restDocs.document(
                            RestDocsUtil.customResponseFields(
                                    "enum-response",
                                    getTitleAttributes("모임 회원 권한 코드"),
                                    enumConvertFieldDescriptor(data)
                            )
                    )
            );
        }
    }

    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(x -> PayloadDocumentation.fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }
}
