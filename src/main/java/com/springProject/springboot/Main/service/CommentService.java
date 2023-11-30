package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.CommentRepository;
import com.springProject.springboot.Main.Repository.UserRepository;
import com.springProject.springboot.Main.entities.Comments;
import com.springProject.springboot.Main.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,UserRepository userRepository){
        this.commentRepository=commentRepository;
        this.userRepository=userRepository;
    }

    public List<Comments> findAllComment(){
        return commentRepository.findAll();
    }

    @Transactional
    public void save(Comments comments){
        comments.setCreatedAt(new Date());
        comments.setUpdatedAt(new Date());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users=userRepository.findByUsername(authentication.getName());
        if(authentication!=null && authentication.isAuthenticated()){
            comments.setName(users.getUsername());
            comments.setEmail(users.getEmail());
        }
        commentRepository.save(comments);
    }

    @Transactional
    public void delete(Long id){
        commentRepository.deleteById(id);
    }

    public Comments findByID(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void update(Comments comments, long id) {
        Comments comment=commentRepository.findById(id).orElse(null);
        comments.setId((int)id);
        comments.setEmail(comment.getEmail());
        comments.setUpdatedAt(new Date());
        comments.setCreatedAt(comment.getCreatedAt());
        comments.setName(comment.getName());
        commentRepository.save(comments);

    }
}
