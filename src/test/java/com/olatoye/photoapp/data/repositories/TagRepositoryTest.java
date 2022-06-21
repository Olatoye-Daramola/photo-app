package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.data.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class TagRepositoryTest {

    Photo firstPhoto;
    Tag firstTag;

    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagRepositoryTest(PhotoRepository photoRepository, TagRepository tagRepository) {
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
    }

    @BeforeEach
    void setUp() {
        Tag aTag = Tag.builder()
                .tagName("newYear")
                .build();

        Tag bTag = Tag.builder()
                .tagName("newMonth")
                .build();

        Photo aPhoto = Photo.builder()
                .imageUrl("unknown.com")
                .numberOfDownloads(2)
                .tags(List.of(aTag, bTag))
                .build();
        firstPhoto = photoRepository.save(aPhoto);
    }

    @Test
    void createTag() {
        firstTag = tagRepository.save(
                Tag.builder()
                        .tagName("newDay")
//                        .photos(Set.of(firstPhoto))
                        .build()
        );
        assertThat(firstTag).isNotNull();
        assertThat(firstTag.getTagName()).isEqualTo("newDay");
    }

    @Test
    void findByTagName() {
        Tag foundTag = tagRepository.findByTagName("newYear").orElse(null);
        assert foundTag != null;
        assertThat(firstPhoto.getTags().contains(foundTag)).isTrue();
    }

    @Test
    void deleteById() {
        firstTag = tagRepository.save(
                Tag.builder()
                        .tagName("newDay")
//                        .photos(Set.of(firstPhoto))
                        .build()
        );
        assertThat(tagRepository.findAll().size()).isEqualTo(3);

        tagRepository.deleteById(firstTag.getId());
        assertThat(tagRepository.findAll().size()).isEqualTo(2);

    }
}