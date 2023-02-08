package com.comeon.backend.api.support.utils;

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
        OAUTH_PROVIDER("../enums", "oauth-provider-code", "소셜 로그인 서비스 벤더 코드"),
        USER_ROLE("../enums", "user-role-code", "유저 권한 코드"),
        USER_STATUS("../enums", "user-status-code", "유저 상태 코드"),
        MEETING_MEMBER_ROLE("../enums", "meeting-member-role-code", "모임 회원 권한 코드"),
        PLACE_CATEGORY_CODE("../enums", "place-category-code", "장소 카테고리 코드"),
        ;

        private final String dirName;
        private final String pageId;
        private final String text;
    }

}
