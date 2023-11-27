package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.CommentRepository;
import com.springProject.springboot.Main.entities.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }

    public List<Comments> findAllComment(){
        return commentRepository.findAll();
    }

    @Transactional
    public void save(Comments comments,int postId){
        comments.setCreatedAt(new Date());
        comments.setUpdatedAt(new Date());
        comments.setPostId(postId);
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
        comments.setPostId(comment.getPostId());
        commentRepository.save(comments);

    }
}
