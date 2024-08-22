package com.fuinco.security.dto;

import lombok.Data;

import java.util.Set;
@Data
public class UserDto {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<RoleDto> roles;

}
