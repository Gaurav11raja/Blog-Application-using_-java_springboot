package com.springProject.springboot.Main.Repository;

import com.springProject.springboot.Main.entities.Roles;
import com.springProject.springboot.Main.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String name);
}
