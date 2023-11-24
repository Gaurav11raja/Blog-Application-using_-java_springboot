package com.springProject.springboot.Main.Repository;

import com.springProject.springboot.Main.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments,Long> {
}
