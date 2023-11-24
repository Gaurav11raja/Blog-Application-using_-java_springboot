package com.springProject.springboot.Main.controller;



import com.springProject.springboot.Main.entities.Comments;
import com.springProject.springboot.Main.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentRepository){
        this.commentService =commentRepository;
    }

    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute("comments") Comments comments) {
        commentService.save(comments);
        return "redirect:/";
    }

    public void showComment(Model model){
        List<Comments> getComment = commentService.findAllComment();
        model.addAttribute("comments",new Comments());
        model.addAttribute("comment",getComment);
    }

    @PostMapping("/editComment/{id}")
    public String editComment(@PathVariable long id){

        return "redirect:/";
    }

    @PostMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable long id){
        commentService.delete(id);
        return "redirect:/";
    }

}
