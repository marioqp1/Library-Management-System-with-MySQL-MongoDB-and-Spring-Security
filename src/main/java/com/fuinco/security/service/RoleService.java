package com.fuinco.security.service;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Role;
import com.fuinco.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public ApiResponse<Role> createRole(Role role) {
        ApiResponse<Role> response = new ApiResponse<>();
        if (role == null) {
            response.setEntity(role);
            response.setMessage("Role is null");
            response.setStatusCode(500);
            response.setSuccess(false);
            return response;
        }
        response.setEntity(roleRepository.save(role));
        response.setMessage("Role saved");
        response.setStatusCode(200);
        response.setSuccess(true);
        return response;

    }

    public ApiResponse<Role> updateRole(Role role) {
        ApiResponse<Role> response = new ApiResponse<>();
        if (role == null) {
            response.setEntity(role);
            response.setMessage("Role is null");
            response.setStatusCode(500);
            response.setSuccess(false);
            return response;
        }
        response.setEntity(roleRepository.save(role));
        response.setMessage("Role updated");
        response.setStatusCode(200);
        response.setSuccess(true);
        return response;
    }

    public ApiResponse<Role> deleteRole(int id) {
        ApiResponse<Role> response = new ApiResponse<>();
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            response.setEntity(roleRepository.save(role.get()));
            response.setMessage("Role deleted");
            response.setStatusCode(200);
            response.setSuccess(true);
            return response;
        }
        //response.setEntity(role.get());
        response.setMessage("Role is null");
        response.setStatusCode(500);
        response.setSuccess(false);
        return response;

    }

    public ApiResponse<Role> findById(int id) {
        ApiResponse<Role> response = new ApiResponse<>();
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            response.setEntity(role.get());
            response.setSuccess(true);
            response.setMessage("Role found");
            response.setStatusCode(200);
        } else {
            response.setSuccess(false);
            response.setMessage("Role not found");
            response.setStatusCode(404);
        }
        return response;
    }

    public ApiResponse<List<Role>> findAll() {
        ApiResponse<List<Role>> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Roles found");
        List<Role> roles = roleRepository.findAll();
        response.setEntity(roles);
        response.setStatusCode(200);
        return response;
    }


//    public ApiResponse<List<UserDto>> RoleId(int id) {
//        ApiResponse<List<UserDto>> response = new ApiResponse<>();
//        if(roleRepository.findById(id).isPresent()) {
//            roleRepository.findBy
//        }
//    }
}
