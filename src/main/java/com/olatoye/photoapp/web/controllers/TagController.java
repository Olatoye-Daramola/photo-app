package com.olatoye.photoapp.web.controllers;


import com.olatoye.photoapp.dtos.requests.TagRequestDto;
import com.olatoye.photoapp.dtos.responses.TagResponseDto;
import com.olatoye.photoapp.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("api/v1/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("")
    public ResponseEntity<?> createTag(@RequestBody TagRequestDto tagRequestDto) {
        try {
            TagResponseDto response = tagService.createTag(tagRequestDto);
            return ResponseEntity.status(CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTagById(@PathVariable("id") Long id) {
        try {
            TagResponseDto response = tagService.findTagById(id);
            return ResponseEntity.status(FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{tagName}")
    public ResponseEntity<?> findTagByTagName(@PathVariable("tagName") String tagName) {
        try {
            TagResponseDto response = tagService.findTagByTagName(tagName);
            return ResponseEntity.status(FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAllTags() {
        //        TODO: Make pageable
        try {
            List<TagResponseDto> responses = tagService.findAll();
            return ResponseEntity.status(FOUND).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTagById(@PathVariable("id") Long id) {
        try  {
            String response = tagService.deleteTagById(id);
            return ResponseEntity.status(OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{tagName}")
    public ResponseEntity<?> deleteTagByTag(@PathVariable("tagName") String tagName) {
        try  {
            String response = tagService.deleteTagByName(tagName);
            return ResponseEntity.status(OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
}
