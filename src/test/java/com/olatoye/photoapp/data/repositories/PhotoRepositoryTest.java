package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Cohort;
import com.olatoye.photoapp.data.models.Native;
import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.data.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PhotoRepositoryTest {

    Native firstNative;
    Photo firstPhoto, secondPhoto;
    Tag aTag, bTag;

    private final NativeRepository nativeRepository;
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    @Autowired
    PhotoRepositoryTest(NativeRepository nativeRepository, PhotoRepository photoRepository,
                        TagRepository tagRepository) {
        this.nativeRepository = nativeRepository;
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
    }


    @BeforeEach
    void setUp() {
        nativeRepository.deleteAll();

        Cohort cohortEight = Cohort.builder()
                .cohortName("Phoenix")
                .cohortNumber(8)
                .build();

        Native aNative = Native.builder()
                .firstName("Native1")
                .lastName("native1")
                .userName("native")
                .cohort(cohortEight)
                .email("n.native1@email.com")
                .build();
        firstNative = nativeRepository.save(aNative);

        aTag = Tag.builder()
                .tagName("newYear")
                .build();

        bTag = Tag.builder()
                .tagName("newMonth")
                .build();

        Photo aPhoto = Photo.builder()
                .imageUrl("unknown.com")
                .imageUploader(firstNative)
                .tags(List.of(aTag, bTag))
                .numberOfDownloads(2)
                .build();
        firstPhoto = photoRepository.save(aPhoto);

        Photo bPhoto = Photo.builder()
                .imageUrl("renown.com")
                .imageUploader(firstNative)
                .tags(List.of(bTag))
                .numberOfDownloads(2)
                .build();
        secondPhoto = photoRepository.save(bPhoto);
    }

    @Test
    void findByImageUploaderId() {
        List<Photo> foundPhotos = photoRepository.findByImageUploaderId(firstNative.getId());
        assertThat(foundPhotos.size()).isEqualTo(2);
    }

    @Test
    void findById() {
        Optional<Photo> foundPhoto = photoRepository.findById(firstPhoto.getId());
        assert foundPhoto.isPresent();
        assertThat(foundPhoto.get().getImageUploader()).isEqualTo(firstNative);
    }

    @Test
    void findByTagName() {
        Tag foundTag = tagRepository.findByTagName("newMonth").orElse(null);
        assert foundTag != null;
        List<Photo> foundPhotos = photoRepository.findAll();
        int count = 0;
        for (Photo photo : foundPhotos)
            if(photo.getTags().contains(bTag))
                ++count;

        assert count == 2;
    }

    @Test
    void deleteByUploader() {
        List<Photo> foundPhotos = photoRepository.findByImageUploaderId(firstNative.getId());
        for(Photo photo : foundPhotos)
            if(photo.getImageUploader() == firstNative)
                photoRepository.deleteById(photo.getId());

        List<Photo> newFoundPhotos = photoRepository.findByImageUploaderId(firstNative.getId());
        assertThat(newFoundPhotos.size()).isEqualTo(0);
    }

    @Test
    void deleteById() {
        assert photoRepository.findById(firstPhoto.getId()).isPresent();
        assertThat(photoRepository.findById(firstPhoto.getId()).get()).isEqualTo(firstPhoto);
        photoRepository.deleteById(firstPhoto.getId());
        assert photoRepository.findById(firstPhoto.getId()).isEmpty();
    }
}
