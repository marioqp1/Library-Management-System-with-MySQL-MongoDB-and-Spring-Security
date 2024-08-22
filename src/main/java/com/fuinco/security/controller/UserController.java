package com.fuinco.security.controller;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.User;
import com.fuinco.security.entity.UserPrincipal;
import com.fuinco.security.service.JwtService;
import com.fuinco.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.test();
        return ResponseEntity.ok(users);
//        ApiResponse<List<User>> response = userService.getAllUsers();
//        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/l")
    public ResponseEntity<List<User>> list() {
        List<User> users = userService.test();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable int id) {
        ApiResponse<User> response = userService.findUserById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser(@PathVariable int id) {
        ApiResponse<Boolean> response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User user) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
                int userId = userDetails.getUserId();
                String token = jwtService.generateToken(user.getUsername(), userId);
                response.setStatusCode(200);
                response.setMessage("Successfully logged in");
                response.setSuccess(true);
                response.setEntity(token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(401);
                response.setMessage("Login failed");
                response.setSuccess(false);
                response.setEntity(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Login failed due to an exception: " + e.getMessage());
            response.setSuccess(false);
            response.setEntity(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<User>> addRole(@PathVariable int id, @RequestParam int roleId) {
        ApiResponse<User> response = userService.addRoleToUser(id, roleId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
