package com.springProject.springboot.Main.controller;

import com.springProject.springboot.Main.Repository.RolesRepository;
import com.springProject.springboot.Main.Repository.UserRepository;
import com.springProject.springboot.Main.entities.*;
import com.springProject.springboot.Main.service.CommentService;
import com.springProject.springboot.Main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class CommentController {
    private CommentService commentService;
    private UserRepository userRepository;
    private PostService postService;
    private RolesRepository rolesRepository;

    @Autowired
    public CommentController(CommentService commentService,PostService postService,
                             RolesRepository rolesRepository, UserRepository userRepository){
        this.commentService=commentService;
        this.userRepository=userRepository;
        this.postService=postService;
        this.rolesRepository=rolesRepository;
    }

    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute("comments") Comments comments,@RequestParam("postId") int postId) {
        Posts post=postService.findByID(postId);
        post.getComments().add(comments);
        postService.savePostComment(post);
        commentService.save(comments);
        return "redirect:/";
    }

    public void showComment(Model model){
        List<Comments> getComment = commentService.findAllComment();
        model.addAttribute("comments",new Comments());
        model.addAttribute("comment",getComment);
    }

    @PostMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable long id,@RequestParam("postId") long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(authentication.getName());
        Comments commentById = commentService.findByID(id);
        Posts post = postService.findByID(postId);
        Roles roles =rolesRepository.findByUsername(user.getUsername());
        if (roles.getRole().equals("ROLE_AUTHOR")) {
            if (!user.getPosts().contains(post)) {
                return "access-denied";
            }
        }
        commentService.delete(id);
        return "redirect:/";
    }
    @PostMapping("/editComment/{id}")
    public String editComment(@PathVariable long id,Model model,@RequestParam("postId") long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(authentication.getName());
        Comments commentById = commentService.findByID(id);
        Posts post = postService.findByID(postId);
        Roles roles =rolesRepository.findByUsername(user.getUsername());
        if (roles.getRole().equals("ROLE_AUTHOR")) {
            if (!user.getPosts().contains(post)) {
                return "access-denied";
            }
        }

        Comments comments = commentService.findByID(id);
        model.addAttribute("comments",comments);

        return "editComment";
    }
    @PostMapping("/updateComment/{id}")
    public String updateComment(@ModelAttribute("comments") Comments comments,@PathVariable long id){
        commentService.update(comments,id);
        return "redirect:/";
    }
}
