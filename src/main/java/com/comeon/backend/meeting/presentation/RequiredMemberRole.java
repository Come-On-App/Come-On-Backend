package com.comeon.backend.meeting.presentation;

import com.comeon.backend.meeting.MemberRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.comeon.backend.meeting.MemberRole.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredMemberRole {

    MemberRole[] value() default {HOST, PARTICIPANT};
}
