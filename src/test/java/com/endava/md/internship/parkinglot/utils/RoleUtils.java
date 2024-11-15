package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.model.Role;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

public class RoleUtils {
    public static Role getPreparedRole(){
        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName(ROLE_REGULAR);

        return role;
    }

    public static String getPreparedRoleName(){
        return "ROLE_REGULAR";
    }
}
