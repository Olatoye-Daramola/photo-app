package com.olatoye.photoapp.services.tag;

import com.olatoye.photoapp.dtos.requests.TagRequestDto;
import com.olatoye.photoapp.dtos.responses.TagResponseDto;

import java.util.List;

public interface TagService {
    TagResponseDto createTag(TagRequestDto tagRequestDto);
    TagResponseDto findTagById(Long id);
    TagResponseDto findTagByTagName(String tagName);
    List<TagResponseDto> findAll();
    String deleteTagById(Long id);
    String deleteTagByName(String tagName);
}
