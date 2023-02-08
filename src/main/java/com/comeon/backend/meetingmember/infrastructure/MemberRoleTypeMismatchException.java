package com.comeon.backend.meetingmember.infrastructure;

import com.comeon.backend.common.error.CommonErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class MemberRoleTypeMismatchException extends RestApiException {

    public MemberRoleTypeMismatchException() {
        super("MemberRole Enum에 정의된 형식이 아닙니다.", CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    public MemberRoleTypeMismatchException(String codeName) {
        super(codeName + "은 MemberRole Enum에 정의된 형식이 아닙니다.", CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
