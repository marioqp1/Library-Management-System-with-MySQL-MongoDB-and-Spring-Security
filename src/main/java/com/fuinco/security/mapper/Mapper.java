//package com.fuinco.security.mapper;
//
//import com.fuinco.security.dto.RoleDto;
//import com.fuinco.security.dto.UserDto;
//import com.fuinco.security.entity.Role;
//import com.fuinco.security.entity.User;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class Mapper {
//    public static UserDto mapUserToDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setUsername(user.getUsername());
//        userDto.setPassword(user.getPassword());
//        userDto.setEnabled(user.isEnabled());
//        if (user.getUserRoles() != null) {
//            Set<RoleDto> roleDtos = user.getUserRoles().stream().map(Mapper::mapRoleToDto).collect(Collectors.toSet());
//            userDto.setRoles(roleDtos);
//        }
//        return userDto;
//    }
//
//    public static User mapDtoToUser(UserDto userDto) {
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setUsername(userDto.getUsername());
//        user.setPassword(userDto.getPassword());
//        user.setEnabled(userDto.isEnabled());
//        if (userDto.getRoles() != null) {
//            List<Role> roles = userDto.getRoles().stream().map(Mapper::mapDtoToRole).collect(Collectors.toList());
//            user.setUserRoles(roles);
//        }
//        return user;
//    }
//
//    public static RoleDto mapRoleToDto(Role role) {
//        RoleDto roleDto = new RoleDto();
//        roleDto.setId(role.getId());
//        roleDto.setName(role.getName());
//        if (role.getUsers() != null) {
//            Set<UserDto> userDtos = role.getUsers().stream().map(Mapper::mapUserToDto).collect(Collectors.toSet());
//            roleDto.setUsers(userDtos);
//        }
//        return roleDto;
//    }
//
//    public static Role mapDtoToRole(RoleDto roleDto) {
//        Role role = new Role();
//        role.setId(roleDto.getId());
//        role.setName(roleDto.getName());
//        if (roleDto.getUsers() != null) {
//            List<User> users = roleDto.getUsers().stream().map(Mapper::mapDtoToUser).collect(Collectors.toList());
//            role.setUsers(users);
//        }
//        return role;
//    }
//}
