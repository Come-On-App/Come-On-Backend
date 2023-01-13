package com.comeon.backend.api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

import java.util.Arrays;
import java.util.Map;

public class RestDocsUtil {

    public static CustomResponseFieldsSnippet customResponseFields(
            String type, Map<String, Object> attributes, FieldDescriptor... descriptors) {

        return new CustomResponseFieldsSnippet(
                type, Arrays.asList(descriptors),
                attributes, true
        );
    }

    public static CustomResponseFieldsSnippet customResponseFields(
            String type, PayloadSubsectionExtractor<?> subsectionExtractor,
            Map<String, Object> attributes, FieldDescriptor... descriptors) {

        return new CustomResponseFieldsSnippet(
                type, subsectionExtractor,
                Arrays.asList(descriptors),
                attributes, true
        );
    }

    public static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:%s/%s.html[%s,role=\"popup\"]", docUrl.dirName, docUrl.pageId, docUrl.text);
    }

    public static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    @Getter
    @RequiredArgsConstructor
    public enum DocUrl {
        COMMON_ERROR_CODE("errors", "common-error-code", "공통 오류 코드"),
        USER_ERROR_CODE("errors", "user-error-code", "유저 오류 코드"),
        ;

        private final String dirName;
        private final String pageId;
        private final String text;
    }

}
