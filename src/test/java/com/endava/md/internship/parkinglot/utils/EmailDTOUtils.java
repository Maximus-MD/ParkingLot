package com.endava.md.internship.parkinglot.utils;

public class EmailDTOUtils {
    public static UserEmail getPreparedDTO() {
        return new UserEmail("Alex@gmail.com");
    }

    public record UserEmail(String email) {
    }
}
