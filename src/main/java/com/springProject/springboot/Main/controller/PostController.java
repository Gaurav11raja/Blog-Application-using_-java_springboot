package com.springProject.springboot.Main.controller;

import com.springProject.springboot.Main.entities.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
    @Controller
    public class PostController {
        private PostService postService;
        private TagsController tagController;
        private CommentController commentController;

        @Autowired
        public PostController(PostService postService,TagsController tagController,CommentController commentController){
            this.postService =postService;
            this.tagController =tagController;
            this.commentController=commentController;
        }

        @GetMapping("/")
        public String showHome(Model model) {
            List<Posts> posts = postService.findAll();
            model.addAttribute("post", posts);
            return "home";
        }

        @GetMapping("/viewPost/{id}")
        public String showPost(@PathVariable long id, Model model){
            commentController.showComment(model);
            Posts posts = postService.findByID(id);
            model.addAttribute("post",posts);
            return "viewPost";
        }

        @GetMapping("/showForm")
        public String showForm(Model model) {
            model.addAttribute("post", new Posts());
            return "form";
        }

        @PostMapping("/savePost")
        public String savePost(@ModelAttribute("post") Posts post, @RequestParam("tags") String tag) {
            tagController.splitTags(tag);
            postService.save(post);
            return "redirect:/";
        }


        @PostMapping("/editPost/{id}")
        public String editPost(@PathVariable long id,Model model){
            Posts posts = postService.findByID(id);
            model.addAttribute("post",posts);
            return "editPost";
        }

        @PostMapping("/updatePost/{id}")
        public String updatePost(@ModelAttribute("post") Posts post,@PathVariable long id){
            postService.update(post,id);
            return "redirect:/";
        }
        @PostMapping("/deletePost/{id}")
        public String deletePost(@PathVariable long id){
            postService.delete(id);
            return "redirect:/";
        }
    }


}
