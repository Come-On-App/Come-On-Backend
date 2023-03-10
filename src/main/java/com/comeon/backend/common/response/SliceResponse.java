package com.comeon.backend.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceResponse<T> {

    private int currentSlice;
    private int sizePerSlice;
    private boolean first;
    private boolean last;
    private boolean hasPrevious;
    private boolean hasNext;
    private int contentsCount;
    private List<T> contents;

    public static<T> SliceResponse<T> toSliceResponse(Slice<T> slice) {
        return SliceResponse.<T>builder()
                .currentSlice(slice.getNumber())
                .sizePerSlice(slice.getSize())
                .first(slice.isFirst())
                .last(slice.isLast())
                .hasPrevious(slice.hasPrevious())
                .hasNext(slice.hasNext())
                .contentsCount(slice.getNumberOfElements())
                .contents(slice.getContent())
                .build();
    }
}
