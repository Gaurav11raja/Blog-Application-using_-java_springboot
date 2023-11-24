package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.PostRepository;
import com.springProject.springboot.Main.entities.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Transactional
    public void save(Posts posts) {
        posts.setIsPublished("Yes");
        posts.setUpdatedAt(new Date());
        posts.setCreatedAt(new Date());
        postRepository.save(posts);
    }


    public List<Posts> findAll() {
        return postRepository.findAll();
    }


    public Posts findByID(long id) {
        return postRepository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(long id) {
        postRepository.deleteById(id);
    }


    @Transactional
    public void update(Posts posts,long id){
        posts.setIsPublished("Yes");
        posts.setId(id);
        Posts post = postRepository.findById(id).orElse(null);
        posts.setCreatedAt(post.getCreatedAt());
        posts.setUpdatedAt(new Date());
        postRepository.save(posts);
    }
}
