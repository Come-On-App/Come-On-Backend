package com.comeon.backend.admin.presentation;

import com.comeon.backend.user.query.UserDao;
import com.comeon.backend.user.query.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class UserQueryController {

    private final UserDao userDao;

    @GetMapping
    public UserDetails myDetails(@RequestParam Long userId) {
        return userDao.findUserDetails(userId);
    }
}
