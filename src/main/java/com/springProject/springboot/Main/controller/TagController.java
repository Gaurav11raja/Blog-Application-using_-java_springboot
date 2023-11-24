package com.springProject.springboot.Main.controller;


import com.springProject.springboot.Main.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TagController {
    private final TagsService tagsService;

    @Autowired
    public TagController(TagsService tagsService){
        this.tagsService=tagsService;
    }
    public void splitTags(String tag) {
        String[] tagName=tag.split(",");
        for(int i=0;i<tagName.length;i++){
            tagsService.saveTag(tagName[i]);
        }
    }
}

