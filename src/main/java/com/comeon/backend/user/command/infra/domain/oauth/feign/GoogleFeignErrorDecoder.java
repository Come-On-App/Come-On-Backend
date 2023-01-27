package com.comeon.backend.user.command.infra.domain.oauth.feign;

import com.comeon.backend.common.error.CommonErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.user.UserErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GoogleFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                throw new RestApiException(methodKey + ": 구글 id-token이 유효하지 않습니다.", UserErrorCode.GOOGLE_ID_TOKEN_INVALID);
            case 500:
                throw new RestApiException(methodKey + ": 구글 서버 오류가 발생하여 id-token을 검증할 수 없습니다.", UserErrorCode.GOOGLE_SERVER_ERROR);
            default:
                throw new RestApiException(methodKey + ": 구글 id-token 검증 오류 발생.", CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
