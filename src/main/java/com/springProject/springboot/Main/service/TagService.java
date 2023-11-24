package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.TagRepository;
import com.springProject.springboot.Main.entities.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class TagsService {
    private final TagRepository tagRepository;

    @Autowired
    public TagsService(TagRepository tagRepository){
        this.tagRepository=tagRepository;
    }

    public void saveTag(String tag) {
        Tags tags=new Tags(tag,new Date(),new Date());
        tagRepository.save(tags);
    }
}

