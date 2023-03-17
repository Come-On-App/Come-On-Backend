package com.comeon.backend.admin.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLockRemoveRequest {

    @NotNull
    private Long userId;
}
