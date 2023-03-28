package com.comeon.backend.common.utils;

import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class SerializeUtils {

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(String s, Class<T> clazz) {
        return clazz.cast(
                SerializationUtils.deserialize(Base64.getUrlDecoder().decode(s))
        );
    }
}
