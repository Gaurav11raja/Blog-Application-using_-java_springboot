package com.springProject.springboot.Main.Repository;

import com.springProject.springboot.Main.entities.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts,Long> {

    Page<Posts> findAllByOrderByCreatedAtDesc(Pageable pageable);
    @Query("SELECT p FROM Posts p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Posts> fullTextSearch(@Param("search") String search, Pageable pageable);
    @Query("SELECT DISTINCT p.author FROM Posts p")
    List<String> findAllAuthors();

    List<Posts> findByAuthorInAndTags_NameIn(List<String> authors, List<String> tags);

    List<Posts> findByAuthorIn(List<String> authors);

    List<Posts> findByTags_NameIn(List<String> tags);
}
