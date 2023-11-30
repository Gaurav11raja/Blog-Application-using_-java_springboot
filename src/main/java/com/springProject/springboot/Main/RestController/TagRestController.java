package com.springProject.springboot.Main.RestController;

import com.springProject.springboot.Main.entities.Posts;
import com.springProject.springboot.Main.entities.Tags;
import com.springProject.springboot.Main.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class TagRestController {
    private final TagsService tagsService;

    @Autowired
    public TagRestController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @PostMapping("/api/saveTags")
    public ResponseEntity<String> saveTags(@RequestBody Posts post, @RequestParam("userTag") String userTag) {
        try {
            String[] tags = userTag.split(",");
            Set<Tags> uniqueTags = new HashSet<>();

            for (String tag : tags) {
                tag = tag.trim();
                Tags existingTag = tagsService.getTagByName(tag);
                if (existingTag != null) {
                    uniqueTags.add(existingTag);
                } else {
                    Tags newTag = new Tags();
                    newTag.setName(tag);
                    tagsService.saveTag(newTag);
                    uniqueTags.add(newTag);
                }
            }

            post.setTags(uniqueTags);

            // You may want to customize the response message based on your needs
            return new ResponseEntity<>("Tags saved successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            // You may want to handle exceptions appropriately
            return new ResponseEntity<>("Error saving tags", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
