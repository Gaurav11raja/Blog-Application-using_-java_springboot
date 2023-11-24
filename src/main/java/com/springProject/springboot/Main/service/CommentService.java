package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.CommentRepository;
import com.springProject.springboot.Main.entities.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentDao){
        this.commentRepository=commentDao;
    }

    public List<Comments> findAllComment(){
        return commentRepository.findAll();
    }

    @Transactional
    public void save(Comments comments){
        commentRepository.save(comments);
    }

    @Transactional
    public void delete(Long id){
        commentRepository.deleteById(id);
    }
}

