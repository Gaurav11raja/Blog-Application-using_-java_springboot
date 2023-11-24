package com.springProject.springboot.Main.Repository;

import com.springProject.springboot.Main.entities.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tags,Long> {
}
