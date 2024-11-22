package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.model.User;

public class UserUtils {
    public static User getPreparedUser() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("Alex@gmail.com");
        user.setName("Alex");
        user.setPhone("0691111111");
        user.setRole(RoleUtils.getPreparedRegularRole());
        user.setPassword("b^nlM71");
        return user;
    }

    public static String getPreparedEmail() {
        return "Alex@gmail.com";
    }
}
