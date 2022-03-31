package com.olatoye.photoapp.services.photo;

import com.olatoye.photoapp.data.models.Cohort;
import com.olatoye.photoapp.data.models.Native;
import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.data.repositories.NativeRepository;
import com.olatoye.photoapp.dtos.requests.PhotoRequest;
import com.olatoye.photoapp.dtos.responses.PhotoResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class PhotoServiceImplTest {

    Native aNative;
    Photo photo;

    private final PhotoService photoService;
    private final NativeRepository nativeRepository;

    @Autowired
    public PhotoServiceImplTest(PhotoService photoService, NativeRepository nativeRepository) {
        this.photoService = photoService;
        this.nativeRepository = nativeRepository;
    }


    @BeforeEach
    void setUp() {
        Cohort cohort = Cohort.builder()
                .name("Phoenix")
                .number(8)
                .build();

        aNative = nativeRepository.save(Native.builder()
                .firstName("Olatoye")
                .lastName("Daramola")
                .cohort(cohort)
                .userName("Olatoye")
                .build());

        photo = Photo.builder()
                .imageUploader(aNative)
                .build();
    }


    @Test
    void createPhoto() throws IOException {
        Path path1 = Paths.get("src/main/resources/images/IMG_3066.jpg");
        Path path2 = Paths.get("src/main/resources/images/DSCN0716.JPG");
        assertThat(path2.toFile().exists()).isTrue();
        MultipartFile multipartFile = new MockMultipartFile(path2.getFileName().toString(), path2.getFileName().toString(),
                "img/jpg", Files.readAllBytes(path2));

        PhotoRequest photoRequest = PhotoRequest.builder()
                .imageUploaderId(aNative.getId())
                .caption("#GodWhen, #Semicolon")
                .image(multipartFile)
                .build();

        PhotoResponse photoResponse = photoService.createPhoto(photoRequest);
        assertThat(photoResponse.getImageUploaderId()).isEqualTo(aNative.getId());
        log.info("\nImage url -->\n{}", photoResponse.getImageUrl());
    }

    @Test
    void findPhotoByPhotoId() {
    }

    @Test
    void findPhotoByNative() {
        List<PhotoResponse> foundPhotos = photoService.findPhotoByNative(aNative.getId());
        log.info("\nImages -->\n{}", foundPhotos);
        assertThat(foundPhotos.size()).isEqualTo(2);
    }

    @Test
    void findPhotoByTagName() {
    }

    @Test
    void findAllPhotos() {
    }

    @Test
    void downloadPhoto() {
    }

    @Test
    void deletePhotoByPhotoId() {
    }

    @Test
    void deletePhotoByNative() {
    }

    @Test
    void deletePhotoByTagName() {
    }

    @Test
    void deleteAllPhotos() {
    }

    @Test
    void testCreatePhoto() {
    }

    @Test
    void testFindPhotoByPhotoId() {
    }

    @Test
    void testFindPhotoByNative() {
    }

    @Test
    void testFindPhotoByTagName() {
    }

    @Test
    void testFindAllPhotos() {
    }

    @Test
    void testDownloadPhoto() {
    }

    @Test
    void testDeletePhotoByPhotoId() {
    }

    @Test
    void testDeletePhotoByNative() {
    }

    @Test
    void testDeletePhotoByTagName() {
    }

    @Test
    void testDeleteAllPhotos() {
    }
}