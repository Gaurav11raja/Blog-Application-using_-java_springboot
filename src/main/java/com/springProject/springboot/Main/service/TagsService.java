package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.TagRepository;
import com.springProject.springboot.Main.entities.Tags;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TagsService {
    private final TagRepository tagRepository;

    @Autowired
    public TagsService(TagRepository tagRepository){
        this.tagRepository=tagRepository;
    }

    @Transactional
    public void saveTag(Tags tag) {
        tagRepository.save(tag);
    }

    public Tags getTagByName(String tagName) {
        return tagRepository.findByName(tagName).orElse(null);
    }
    public List<Tags> getAllTags() {
        return tagRepository.findAll();
    }
}
