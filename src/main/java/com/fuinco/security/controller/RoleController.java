package com.fuinco.security.controller;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.Role;
import com.fuinco.security.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles") // Changed to plural for consistency with REST conventions
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> getRoles() {
        ApiResponse<List<Role>> response = roleService.findAll();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Role>> createRole(@RequestBody Role role) {
        ApiResponse<Role> response = roleService.createRole(role);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update") // Changed from POST to PUT for update operation
    public ResponseEntity<ApiResponse<Role>> updateRole(@RequestBody Role role) {
        ApiResponse<Role> response = roleService.updateRole(role);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> getRole(@PathVariable int id) {
        ApiResponse<Role> response = roleService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Role>> deleteRole(@PathVariable int id) {
        ApiResponse<Role> response = roleService.deleteRole(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
