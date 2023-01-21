package com.comeon.backend.api.response;

import com.comeon.backend.api.response.controller.CommonResponseRestDocsController;
import com.comeon.backend.api.utils.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CommonResponseRestDocsController.class)
public class CommonResponseRestDocsTest extends RestDocsTestSupport {

    @Nested
    @DisplayName("Slice 응답 스펙")
    class sliceResponse {
        @Test
        @DisplayName("success")
        void success() throws Exception {
            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/response/slice")
                            .accept(MediaType.APPLICATION_JSON)
            );

            // docs
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(
                            restDocs.document(
                                    PayloadDocumentation.responseFields(
                                            getTitleAttributes("Slice 응답 필드"),
                                            PayloadDocumentation.fieldWithPath("currentSlice").type(JsonFieldType.NUMBER).description("현재 페이지 번호. 0부터 시작."),
                                            PayloadDocumentation.fieldWithPath("sizePerSlice").type(JsonFieldType.NUMBER).description("한 페이지당 contents 필드 내부 데이터의 개수"),
                                            PayloadDocumentation.fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("처음 페이지인지 여부"),
                                            PayloadDocumentation.fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지 여부"),
                                            PayloadDocumentation.fieldWithPath("hasPrevious").type(JsonFieldType.BOOLEAN).description("이전 페이지의 존재 여부"),
                                            PayloadDocumentation.fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지의 존재 여부"),
                                            PayloadDocumentation.fieldWithPath("contentsCount").type(JsonFieldType.NUMBER).description("현재 페이지의 contents 필드 내부 데이터 개수"),
                                            PayloadDocumentation.fieldWithPath("contents").type(JsonFieldType.ARRAY).description("요청에 대한 실제 응답 데이터 필드")
                                    )
                            )
                    );
        }
    }
}
