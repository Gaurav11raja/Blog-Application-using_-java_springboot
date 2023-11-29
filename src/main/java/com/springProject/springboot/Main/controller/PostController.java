package com.springProject.springboot.Main.controller;


import com.springProject.springboot.Main.entities.Posts;
import com.springProject.springboot.Main.entities.Tags;
import com.springProject.springboot.Main.service.PostService;
import com.springProject.springboot.Main.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Controller
public class PostController {
    private PostService postService;
    private TagController tagController;
    private CommentController commentController;
    private TagsService tagService;

    @Autowired
    public PostController(PostService postService,TagController tagController,CommentController commentController,TagsService tagsService){
        this.postService =postService;
        this.tagController =tagController;
        this.commentController=commentController;
        this.tagService=tagsService;
    }

    @GetMapping("/")
    public String showHome(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "4") int size,
                           @RequestParam(name = "sortOrder", required = false) String sortOrder,
                           @RequestParam(name = "search", required = false) String search) {

        if (search != null && !search.isEmpty()) {
            Page<Posts> postsPage = postService.fullTextSearch(search, PageRequest.of(page, size));
            model.addAttribute("post", postsPage);
        } else {
            Page<Posts> postsPage = postService.findPaginated(page, size, sortOrder);
            model.addAttribute("post", postsPage);
            model.addAttribute("sortOrder",sortOrder);
        }
        List<Tags> allTags = tagService.getAllTags();
        model.addAttribute("allAuthor",postService.getAllAuthors());
        model.addAttribute("allTags", allTags);
        model.addAttribute("currentPage", page);

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
    public String savePost(@ModelAttribute("post") Posts post, @RequestParam("tagStore") String userTag) {
        tagController.saveTags(post,userTag);
        postService.savePost(post);
        return "redirect:/";
    }


    @PostMapping("/editPost/{id}")
    public String editPost(@PathVariable long id,Model model){
        Posts posts = postService.findByID(id);
        Set<Tags> tags = posts.getTags();
        StringBuilder tagName = new StringBuilder();
        for(Tags tag : tags){
            tagName.append(tag.getName());
            tagName.append(",");
        }
        if (tagName.length() > 0) {
            tagName.deleteCharAt(tagName.length() - 1);
        }
        model.addAttribute("post",posts);
        model.addAttribute("tag",tagName.toString());
        return "editPost";
    }

    @PostMapping("/updatePost/{id}")
    public String updatePost(@ModelAttribute("post") Posts post,@RequestParam("tag") String tag,@PathVariable long id){
        tagController.saveTags(post,tag);
        postService.update(post,id);
        return "redirect:/";
    }
    @PostMapping("/deletePost/{id}")
    public String deletePost(@PathVariable long id){
        postService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filterPosts(@RequestParam(name = "author",required = false) List<String> author,
                              @RequestParam(name = "tags",required = false) List<String> tags,
                              Model model) {
        List<Posts> filteredPosts = postService.filterPosts(author, tags);
        model.addAttribute("post", filteredPosts);

        return "filter";
    }

}

