package com.olatoye.photoapp.services.tag;

import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.data.models.Tag;
import com.olatoye.photoapp.data.repositories.PhotoRepository;
import com.olatoye.photoapp.data.repositories.TagRepository;
import com.olatoye.photoapp.dtos.requests.TagRequestDto;
import com.olatoye.photoapp.dtos.responses.TagResponseDto;
import com.olatoye.photoapp.utils.ModelMapper;
import com.olatoye.photoapp.web.exceptions.PhotoNotFoundException;
import com.olatoye.photoapp.web.exceptions.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, PhotoRepository photoRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.photoRepository = photoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagResponseDto createTag(TagRequestDto tagRequestDto) {
        Photo photo = photoRepository.findById(tagRequestDto.getPhoto().getId()).orElseThrow(
                () -> new PhotoNotFoundException("Photo does not exist")
        );

        Optional<Tag> foundTag = tagRepository.findByTagName(tagRequestDto.getTagName());
        if (foundTag.isPresent()) {
            foundTag.get().getPhotos().add(photo);
            return modelMapper.map(foundTag.get());
        }

        Tag tag = tagRepository.save(Tag.builder()
                .tagName(tagRequestDto.getTagName())
                .photos(List.of(photo))
                .build());
        return modelMapper.map(tag);
    }

    @Override
    public TagResponseDto findTagById(Long id) {
        return modelMapper.map(tagRepository.findById(id).orElseThrow(
                ()-> new TagNotFoundException("Tag not found")
        ));
    }

    @Override
    public TagResponseDto findTagByTagName(String tagName) {
        return modelMapper.map(tagRepository.findByTagName(tagName).orElseThrow(
                ()-> new TagNotFoundException("Tag not found")
        ));
    }

    @Override
    public List<TagResponseDto> findAll() {
        List<Tag> foundTags = tagRepository.findAll();
        List<TagResponseDto> responses = new ArrayList<>();
        for (Tag tag : foundTags) {
            responses.add(modelMapper.map(tag));
        }
        return responses;
    }

    @Override
    public String deleteTagById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                ()->  new TagNotFoundException("Tag not found")
        );
        tagRepository.deleteById(tag.getId());
        return "Tag has been successfully deleted";
    }

    @Override
    public String deleteTagByName(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(
                ()->  new TagNotFoundException("Tag not found")
        );
        tagRepository.deleteById(tag.getId());
        return "Tag has been successfully deleted";
    }
}
