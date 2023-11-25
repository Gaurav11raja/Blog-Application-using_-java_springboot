package com.springProject.springboot.Main.Repository;

import com.springProject.springboot.Main.entities.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts,Long> {
    Page<Posts> findAllByOrderByCreatedDesc(Pageable pageable);
}
