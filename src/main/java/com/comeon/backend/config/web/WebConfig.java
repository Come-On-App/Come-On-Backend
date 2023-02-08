package com.comeon.backend.config.web;

import com.comeon.backend.config.web.member.MemberAuthorizationInterceptor;
import com.comeon.backend.config.web.log.MultipartRequestLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberAuthorizationInterceptor memberAuthorizationInterceptor;
    private final MultipartRequestLogInterceptor multipartRequestLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(multipartRequestLogInterceptor)
                .addPathPatterns("/api/v1/image");

        registry.addInterceptor(memberAuthorizationInterceptor);
    }
}
