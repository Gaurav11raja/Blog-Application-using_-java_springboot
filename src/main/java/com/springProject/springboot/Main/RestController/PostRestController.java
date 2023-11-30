package com.springProject.springboot.Main.RestController;

import com.springProject.springboot.Main.Repository.RolesRepository;
import com.springProject.springboot.Main.Repository.UserRepository;
import com.springProject.springboot.Main.entities.Posts;
import com.springProject.springboot.Main.entities.Roles;
import com.springProject.springboot.Main.entities.Users;
import com.springProject.springboot.Main.service.PostService;
import com.springProject.springboot.Main.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {
    private final PostService postService;
    private UserRepository userRepository;
    private RolesRepository rolesRepository;

    @Autowired
    public PostRestController(PostService postService,UserRepository userRepository,
                              RolesRepository rolesRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }




    @GetMapping("/posts")
    public ResponseEntity<Page<Posts>> showHome(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "4") int size,
                                                @RequestParam(name = "sortOrder", required = false) String sortOrder,
                                                @RequestParam(name = "search", required = false) String search) {
        Page<Posts> postsPage;
        if (search != null && !search.isEmpty()) {
            postsPage = postService.fullTextSearch(search, PageRequest.of(page, size));
        } else {
            postsPage = postService.findPaginated(page, size, sortOrder);
        }
        return new ResponseEntity<>(postsPage, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Posts> showPost(@PathVariable long id) {
        Posts post = postService.findByID(id);
        return post != null ? new ResponseEntity<>(post, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addpost")
    public ResponseEntity<String> savePost(@RequestBody Posts post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findByUsername(authentication.getName());
        users.getPosts().add(post);
        userRepository.save(users);

        postService.savePost(post, users.getUsername());
        return new ResponseEntity<>("Post saved successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<String> updatePost(@RequestBody Posts post, @RequestParam("tag") String tag, @PathVariable long id) {

        postService.update(post, id);
        return new ResponseEntity<>("Post updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(authentication.getName());
        Posts post = postService.findByID(id);
        Roles roles = rolesRepository.findByUsername(user.getUsername());
        if (roles.getRole().equals("ROLE_AUTHOR")) {
            if (!user.getPosts().contains(post)) {
                return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
            }
        }
        postService.delete(id);
        return new ResponseEntity<>("Post deleted successfully!", HttpStatus.OK);
    }


    @PostMapping("/filter")
    public ResponseEntity<List<Posts>> filterPosts(@RequestParam(name = "author", required = false) List<String> author,
                                                   @RequestParam(name = "tags", required = false) List<String> tags) {
        List<Posts> filteredPosts = postService.filterPosts(author, tags);
        return new ResponseEntity<>(filteredPosts, HttpStatus.OK);
    }
}
