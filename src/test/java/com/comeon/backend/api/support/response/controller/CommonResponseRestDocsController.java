package com.comeon.backend.api.support.response.controller;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.common.response.SliceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonResponseRestDocsController {

    @GetMapping("/response/slice")
    public SliceResponse<?> sliceResponse() {
        return SliceResponse.toSliceResponse(new SliceImpl<>(List.of(1, 2, 3), Pageable.ofSize(5), true));
    }

    @GetMapping("/response/list")
    public ListResponse<?> listResponse() {
        return ListResponse.toListResponse(List.of(1, 2, 3));
    }
}
