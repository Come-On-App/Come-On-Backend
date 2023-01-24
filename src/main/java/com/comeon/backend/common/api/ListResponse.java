package com.comeon.backend.common.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListResponse<T> {

    private int contentsCount;
    private List<T> contents;

    public static<T> ListResponse<T> toListResponse(List<T> list) {
        return ListResponse.<T>builder()
                .contentsCount(list.size())
                .contents(list)
                .build();
    }
}
