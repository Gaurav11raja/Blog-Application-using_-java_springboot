package com.springProject.springboot.Main.Repository;

import com.springProject.springboot.Main.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findByUsername(String username);
}
