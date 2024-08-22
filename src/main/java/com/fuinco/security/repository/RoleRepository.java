package com.fuinco.security.repository;

import com.fuinco.security.entity.Role;
import com.fuinco.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}
