package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Like;
import com.olatoye.photoapp.data.models.Native;
import com.olatoye.photoapp.data.models.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class LikeRepositoryTest {

    private Native savedNative;
    private Photo savedPhoto;
    private Like savedLike;


    private final PhotoRepository photoRepository;
    private final NativeRepository nativeRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public LikeRepositoryTest(PhotoRepository photoRepository, NativeRepository nativeRepository, LikeRepository likeRepository) {
        this.photoRepository = photoRepository;
        this.nativeRepository = nativeRepository;
        this.likeRepository = likeRepository;
    }

    @BeforeEach
    void setUp() {
        Native aNative = Native.builder()
                .userName("aNative")
                .build();
        savedNative = nativeRepository.save(aNative);

        Photo aPhoto = Photo.builder()
                .imageUrl("eweee.com")
                .imageUploader(aNative)
                .build();
        savedPhoto = photoRepository.save(aPhoto);

        Like aLike = Like.builder()
                .photo(aPhoto)
                .liker(aNative)
                .build();
        savedLike = likeRepository.save(aLike);
    }

    @Test
    void createLike() {
        Native bNative = nativeRepository.save(Native.builder()
                .userName("bNative")
                .build());
        Photo bPhoto = photoRepository.save(Photo.builder()
                .imageUrl("Jesus.com")
                .imageUploader(bNative)
                .build());
        assertThat(likeRepository.findAll().size()).isEqualTo(1);
        Like bLike = likeRepository.save(Like.builder()
                .liker(bNative)
                .photo(bPhoto)
                .build());
        assertThat(likeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void findById() {
        Like foundLike = likeRepository.findById(savedLike.getId()).orElse(null);
        assert foundLike != null;
        assertThat(foundLike.getPhoto()).isEqualTo(savedPhoto);
    }

    @Test
    void findByPhoto() {
        List<Like> foundLikes = likeRepository.findByPhotoId(savedPhoto.getId());
        assertThat(foundLikes.size()).isEqualTo(1);
    }

    @Test
    void findByLiker() {
        List<Like> foundLikes = likeRepository.findByLikerId(savedNative.getId());
        assertThat(foundLikes.size()).isEqualTo(1);
    }

    @Test
    void deleteById() {
        assertThat(likeRepository.findAll().size()).isEqualTo(1);
        likeRepository.deleteById(savedLike.getId());
        assertThat(likeRepository.findAll().size()).isEqualTo(0);
    }
}