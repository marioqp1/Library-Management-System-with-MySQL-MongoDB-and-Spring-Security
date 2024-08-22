package com.fuinco.security.service;

import com.fuinco.security.entity.User;
import com.fuinco.security.entity.UserPrincipal;
import com.fuinco.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        System.out.println(userPrincipal.getUsername() + " " + userPrincipal.getPassword());

        return userPrincipal;
    }
}
