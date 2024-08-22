package com.fuinco.security.service;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Role;
import com.fuinco.security.entity.User;
import com.fuinco.security.repository.RoleRepository;
import com.fuinco.security.repository.UserRepository;
import com.fuinco.security.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> test() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public ApiResponse<User> save(User userDto) {
        ApiResponse<User> response = new ApiResponse<>();
        if (userRepository.existsByUsername(userDto.getUsername())) {
            response.setMessage("User already exists by username: " + userDto.getUsername());
            response.setStatusCode(500);
            response.setEntity(null);
            response.setSuccess(false);
            return response;
        }
        userDto.setPassword(Utils.getMd5(userDto.getPassword()));

        try {
            User savedUser = userRepository.save(userDto);
            userDto.setId(savedUser.getId());
            addRoleToUser(savedUser.getId(), 1);
            response.setEntity(userDto);
            response.setSuccess(true);
            response.setMessage("success");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setEntity(userDto);
            response.setSuccess(false);
            response.setMessage("failed");
        }
        return response;
    }

    public ApiResponse<List<User>> getAllUsers() {
        ApiResponse<List<User>> response = new ApiResponse<>();
        List<User> users = userRepository.findAll();
        //List<UserDto> userDTOs = users.stream().map(Mapper::mapUserToDto).collect(Collectors.toList());
        response.setEntity(users);
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setMessage("All users successfully");
        return response;
    }

    public ApiResponse<Boolean> deleteUser(int userId) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
            response.setMessage("Deleted user successfully");
            response.setSuccess(true);
            response.setStatusCode(200);
            response.setEntity(true);
        } else {
            response.setSuccess(false);
            response.setMessage("User not found");
            response.setStatusCode(404);
            response.setEntity(false);
        }
        return response;
    }

    public ApiResponse<User> findUserById(int userId) {
        ApiResponse<User> response = new ApiResponse<>();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            response.setSuccess(true);
            response.setMessage("success");
            response.setStatusCode(200);
            response.setEntity(userOptional.get());
        } else {
            response.setSuccess(false);
            response.setMessage("failed");
            response.setStatusCode(404);
        }
        return response;
    }

    public ApiResponse<User> addRoleToUser(int userId, int roleId) {
        ApiResponse<User> response = new ApiResponse<>();
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (!roleOptional.isPresent()) {
            response.setSuccess(false);
            response.setStatusCode(404);
            response.setMessage("Role not found");
            return response;
        }
        if (!userOptional.isPresent()) {
            response.setSuccess(false);
            response.setStatusCode(404);
            response.setMessage("User not found");
            return response;
        }
        User user = userOptional.get();
        Role role = roleOptional.get();
        List<Role> currentRoles = user.getUserRoles();
        int roleID = role.getId();
        for (int i = 0; i < currentRoles.size(); i++) {
            if (roleID == currentRoles.get(i).getId()) {
                response.setSuccess(false);
                response.setStatusCode(409);
                response.setMessage("Role already exists");
                return response;
            }
        }
        currentRoles.add(role);
        user.setUserRoles(currentRoles);
        userRepository.save(user);
        response.setEntity(user);
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setMessage("Role added successfully");


        return response;
    }
}
