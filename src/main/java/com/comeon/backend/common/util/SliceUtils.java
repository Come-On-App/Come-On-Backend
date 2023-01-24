package com.comeon.backend.common.util;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class SliceUtils {

    public static boolean hasNext(Pageable pageable, List<?> contents) {
        if (contents.size() > pageable.getPageSize()) {
            for (int i = 0; i < contents.size() - pageable.getPageSize(); i++) {
                contents.remove(pageable.getPageSize());
            }
            return true;
        }
        return false;
    }
}
