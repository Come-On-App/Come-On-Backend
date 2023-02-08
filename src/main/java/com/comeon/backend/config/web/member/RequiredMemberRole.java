package com.comeon.backend.config.web.member;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredMemberRole {

    MemberRole[] value() default {MemberRole.HOST, MemberRole.PARTICIPANT};

}
