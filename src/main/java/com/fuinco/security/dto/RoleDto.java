package com.fuinco.security.dto;

import lombok.Data;

import java.util.Set;
@Data
public class RoleDto {
    private int id;
    private String name;
    private Set<UserDto> users;

    // Getters and setters
}
