package com.comeon.backend.user.command.infra.domain.oauth.feign;

import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.user.UserErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KakaoApiFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                throw new RestApiException(methodKey + ": 카카오 엑세스 토큰이 유효하지 않아 유저 정보를 받아올 수 없습니다.", UserErrorCode.KAKAO_ACCESS_TOKEN_INVALID);
            case 500:
                throw new RestApiException(methodKey + ": 카카오 서버 오류가 발생하여 유저 정보를 받아올 수 없습니다.", UserErrorCode.KAKAO_SERVER_ERROR);
            default:
                throw new RestApiException(methodKey + ": 카카오 유저 정보 받기 오류 발생.", CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
