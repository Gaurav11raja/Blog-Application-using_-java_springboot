package com.springProject.springboot.Main.RestController;

import com.springProject.springboot.Main.Repository.RolesRepository;
import com.springProject.springboot.Main.Repository.UserRepository;
import com.springProject.springboot.Main.entities.Comments;
import com.springProject.springboot.Main.entities.Posts;
import com.springProject.springboot.Main.entities.Roles;
import com.springProject.springboot.Main.entities.Users;
import com.springProject.springboot.Main.service.CommentService;
import com.springProject.springboot.Main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final PostService postService;
    private final RolesRepository rolesRepository;

    @Autowired
    public CommentRestController(CommentService commentService, UserRepository userRepository,
                                 PostService postService, RolesRepository rolesRepository) {
        this.commentService = commentService;
        this.userRepository = userRepository;
        this.postService = postService;
        this.rolesRepository = rolesRepository;
    }

    @PostMapping("/saveComment")
    public ResponseEntity<String> saveComment(@RequestBody Comments comments) {
        commentService.save(comments);
        return new ResponseEntity<>("Comment saved successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<List<Comments>> getAllComments() {
        List<Comments> comments = commentService.findAllComment();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id,@RequestParam("post_id") long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(authentication.getName());
        Posts post = postService.findByID(postId);
        Roles roles = rolesRepository.findByUsername(user.getUsername());
        if (roles.getRole().equals("ROLE_AUTHOR")) {
            if (!user.getPosts().contains(post)) {
                return new ResponseEntity<>("Access Denied!", HttpStatus.FORBIDDEN);
            }
        }
        commentService.delete(id);
        return new ResponseEntity<>("Comment deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/editComment/{id}")
    public ResponseEntity<Comments> editComment(@PathVariable long id,@RequestParam("postId") long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(authentication.getName());
        Comments comments = commentService.findByID(id);
        Posts post = postService.findByID(postId);
        Roles roles = rolesRepository.findByUsername(user.getUsername());
        if (roles.getRole().equals("ROLE_AUTHOR")) {
            if (!user.getPosts().contains(post)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<String> updateComment(@RequestBody Comments comments, @PathVariable long id) {
        commentService.update(comments, id);
        return new ResponseEntity<>("Comment updated successfully!", HttpStatus.OK);
    }
}
