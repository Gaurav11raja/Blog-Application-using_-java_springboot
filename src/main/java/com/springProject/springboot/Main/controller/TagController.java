package com.springProject.springboot.Main.controller;

import com.springProject.springboot.Main.entities.Posts;
import com.springProject.springboot.Main.entities.Tags;
import com.springProject.springboot.Main.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class TagController {
    private final TagsService tagsService;

    @Autowired
    public TagController(TagsService tagsService){
        this.tagsService=tagsService;
    }

    public void saveTags(Posts post, String userTag) {
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
    }

}
