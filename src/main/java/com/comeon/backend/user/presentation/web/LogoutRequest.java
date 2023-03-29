package com.comeon.backend.user.presentation.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest implements Serializable {

    private String atk;
    private String redirectUri;

    @Override
    public String toString() {
        return "atk: " + atk + ", redirectUri: " + redirectUri;
    }
}
