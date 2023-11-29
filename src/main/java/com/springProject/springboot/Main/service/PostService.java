package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.PostRepository;
import com.springProject.springboot.Main.Repository.TagRepository;
import com.springProject.springboot.Main.entities.Posts;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostService(PostRepository postRepository,TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository=tagRepository;
    }


    @Transactional
    public void savePost(Posts posts,String author) {
        posts.setIsPublished("Yes");
        posts.setUpdatedAt(new Date());
        posts.setCreatedAt(new Date());
        posts.setAuthor(author);
        postRepository.save(posts);
    }



    public Posts findByID(long id) {
        return postRepository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(long id) {
        postRepository.deleteById(id);
    }

    public Page<Posts> findPaginated(int page, int size, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortOrder != null)
        {
            if(sortOrder.equals("desc")){
            return postRepository.findAllByOrderByCreatedAtDesc(pageable);
            }else {
                return postRepository.findAllByOrderByCreatedAtAsc(pageable);
            }
        }
        else
            return postRepository.findAll(pageable);
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
    public Page<Posts> fullTextSearch(String search, Pageable pageable) {
        return postRepository.fullTextSearch(search, pageable);
    }

    public List<String> getAllAuthors() {
        return postRepository.findAllAuthors();
    }

    public List<Posts> filterPosts(List<String> authors, List<String> tags) {
        if (authors != null && tags != null) {
            return postRepository.findByAuthorInAndTags_NameIn(authors, tags);
        } else if (authors != null) {
            return postRepository.findByAuthorIn(authors);
        } else if (tags != null) {
            return postRepository.findByTags_NameIn(tags);
        } else {
            return Collections.emptyList(); // or return all posts
        }
    }
}
