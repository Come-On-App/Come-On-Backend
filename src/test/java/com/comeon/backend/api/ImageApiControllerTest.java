package com.comeon.backend.api;

import com.comeon.backend.api.utils.RestDocsTestSupport;
import com.comeon.backend.config.S3MockConfig;
import com.comeon.backend.image.infrastructure.S3FileManager;
import com.comeon.backend.image.api.ImageApiController;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

@Import({
        S3MockConfig.class,
        S3FileManager.class,
})
@WebMvcTest(ImageApiController.class)
public class ImageApiControllerTest extends RestDocsTestSupport {

    @Nested
    @DisplayName("이미지 업로드")
    class uploadImage {

        String endpoint = "/api/v1/image";

        @Test
        @DisplayName("given: 인증 필요, image 파라미터에 이미지 전송 -> then: HTTP 200, 업로드된 이미지 URL 반환")
        void upload() throws Exception {
            //given
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "image",
                    "test-img.png",
                    ContentType.IMAGE_PNG.getMimeType(),
                    new FileInputStream(ResourceUtils.getFile(this.getClass().getResource("/static/test-img.png")))
            );

            //when
            ResultActions perform = mockMvc.perform(
                    RestDocumentationRequestBuilders.multipart(endpoint)
                            .file(mockMultipartFile)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentRequestATK.getToken())
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .characterEncoding(StandardCharsets.UTF_8)
            );

            //then
            perform.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").isNotEmpty());

            // docs
            perform.andDo(
                    restDocs.document(
                            HeaderDocumentation.requestHeaders(
                                    getTitleAttributes("요청 헤더"),
                                    authorizationHeaderDescriptor
                            ),
                            RequestDocumentation.requestParts(
                                    getTitleAttributes("요청 파트"),
                                    RequestDocumentation.partWithName("image").description("업로드할 이미지 파일")
                            ),
                            PayloadDocumentation.responseFields(
                                    getTitleAttributes("응답 필드"),
                                    PayloadDocumentation.subsectionWithPath("imageUrl").type(JsonFieldType.STRING).description("업로드된 이미지의 URL")
                            )
                    )
            );
        }
    }
}
