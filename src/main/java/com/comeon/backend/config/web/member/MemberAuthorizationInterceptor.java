package com.comeon.backend.config.web.member;

import com.comeon.backend.common.error.TypeMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthorizationInterceptor implements HandlerInterceptor {

    private final UserProvider userProvider;
    private final MemberRoleService memberRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiredMemberRole requiredMemberRoleAnnotation = handlerMethod.getMethodAnnotation(RequiredMemberRole.class);
        if (requiredMemberRoleAnnotation == null) return true;

        Map<String, String> pathVariables
                = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long meetingId = convertMeetingId(pathVariables.get("meetingId"));

        Long userId = userProvider.getUserId().orElseThrow();

        MemberRole memberRole = memberRoleService.getMemberRoleBy(meetingId, userId);

        MemberRole[] roles = requiredMemberRoleAnnotation.value();
        if (Arrays.stream(roles).noneMatch(role -> role == memberRole)) {
            String roleNames = Arrays.stream(roles).map(MemberRole::name).collect(Collectors.joining(" | "));
            throw new MemberAuthorizationException("required: " + roleNames + ", but client role: " + memberRole.name());
        }

        return true;
    }

    private Long convertMeetingId(String meetingIdStr) {
        if (!StringUtils.hasText(meetingIdStr)) {
            throw new TypeMismatchException();
        }

        try {
            return Long.valueOf(meetingIdStr);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException(e);
        }
    }
}
