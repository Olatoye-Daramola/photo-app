package com.olatoye.photoapp.services.photo;

import com.cloudinary.utils.ObjectUtils;
import com.olatoye.photoapp.dtos.requests.PhotoRequestDto;
import com.olatoye.photoapp.dtos.responses.NativeResponseDto;
import com.olatoye.photoapp.dtos.responses.PhotoResponseDto;
import com.olatoye.photoapp.services.nativeFolder.NativeService;
import com.olatoye.photoapp.utils.ModelMapper;
import com.olatoye.photoapp.data.models.Native;
import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.data.models.Tag;
import com.olatoye.photoapp.data.repositories.NativeRepository;
import com.olatoye.photoapp.data.repositories.PhotoRepository;
import com.olatoye.photoapp.data.repositories.TagRepository;
import com.olatoye.photoapp.services.cloud.CloudService;
import com.olatoye.photoapp.web.exceptions.NativeNotFoundException;
import com.olatoye.photoapp.web.exceptions.PhotoAppException;
import com.olatoye.photoapp.web.exceptions.PhotoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final NativeService nativeService;
    private final TagRepository tagRepository;
    private final CloudService cloudService;
    private final ModelMapper modelMapper;

    private Integer numberOfDownloads;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, NativeService nativeService,
                            CloudService cloudService, ModelMapper modelMapper, TagRepository tagRepository) {
        this.photoRepository = photoRepository;
        this.nativeService = nativeService;
        this.tagRepository = tagRepository;
        this.cloudService = cloudService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PhotoResponseDto createPhoto(PhotoRequestDto photoRequestDto) {
        NativeResponseDto foundNative = nativeService.findById(photoRequestDto.getImageUploaderId());
        Native aNative = modelMapper.map(foundNative);

        List<Tag> tags = extractTags(photoRequestDto.getCaption());

//        String uploaderName = aNative.getFirstName() + "_" + aNative.getLastName();
        Map<?, ?> uploadedPhoto = uploadPhoto(photoRequestDto.getImage(), aNative);

        Photo photo = photoRepository.save(Photo.builder()
                .imageUploader(aNative)
                .tags(tags)
                .imageUrl(uploadedPhoto.get("url").toString())
                .numberOfDownloads(0)
                .build());
        return modelMapper.map(photo);
    }

    @Override
    public PhotoResponseDto findPhotoByPhotoId(Long photoId) {
        Photo foundPhoto = photoRepository.findById(photoId).orElseThrow(
                () -> new PhotoNotFoundException("Photo with id:" + photoId + " not found")
        );
        return modelMapper.map(foundPhoto);
    }

    @Override
    public List<PhotoResponseDto> findPhotoByNative(Long nativeId) {
        List<Photo> foundPhotos = photoRepository.findByImageUploaderId(nativeId);
        if (foundPhotos.isEmpty()) return null;

        List<PhotoResponseDto> photoResponseDtos = new ArrayList<>();
        for (Photo photo : foundPhotos)
            photoResponseDtos.add(modelMapper.map(photo));
        return photoResponseDtos;
    }

    @Override
    public List<PhotoResponseDto> findPhotoByNativeName(String firstNameOrLastName) {
        List<Photo> foundPhotos = photoRepository.findByImageUploaderFirstName(firstNameOrLastName);
        foundPhotos.addAll(photoRepository.findByImageUploaderLastName(firstNameOrLastName));
        if (foundPhotos.isEmpty()) return null;

        List<PhotoResponseDto> photoResponseDtos = new ArrayList<>();
        for (Photo photo : foundPhotos)
            photoResponseDtos.add(modelMapper.map(photo));
        return photoResponseDtos;
    }

    @Override
    public List<PhotoResponseDto> findPhotoByTagName(String tagName) {
        Tag foundTag = tagRepository.findByTagName(tagName).orElseThrow(
                () -> new PhotoAppException("Tag not found"));

        List<Photo> foundPhotos = photoRepository.findAll();
        if (foundPhotos.isEmpty()) return null;

        List<PhotoResponseDto> photoResponseDtos = new ArrayList<>();
        for (Photo photo : foundPhotos) {
            if (photo.getTags().contains(foundTag))
                photoResponseDtos.add(modelMapper.map(photo));
        }
        return photoResponseDtos;
    }

    @Override
    public List<PhotoResponseDto> findAllPhotos() {
        List<Photo> foundPhotos = photoRepository.findAll();
        List<PhotoResponseDto> photoResponseDtos = new ArrayList<>();

        for (Photo photo : foundPhotos)
            photoResponseDtos.add(modelMapper.map(photo));
        return photoResponseDtos;
    }

    @Override
    public ByteArrayResource downloadPhoto(PhotoResponseDto photoResponseDto) throws IOException {
        Photo foundPhoto = photoRepository.findById(photoResponseDto.getImageId()).orElseThrow(
                () -> new PhotoNotFoundException(BAD_REQUEST.toString())
        );
        int downloadCount = foundPhoto.getNumberOfDownloads();
        foundPhoto.setNumberOfDownloads(++downloadCount);
        PhotoResponseDto modifiedPhotoResponseDto = modelMapper.map(foundPhoto);

        Path path = Paths.get(modifiedPhotoResponseDto.getImageUrl());
        return new ByteArrayResource(Files.readAllBytes(path));
    }

    @Override
    public String deletePhotoByPhotoId(Long photoId) {
        Photo foundPhoto = photoRepository.findById(photoId).orElseThrow(
                () -> new PhotoNotFoundException("Photo with id:" + photoId + " not found")
        );
        photoRepository.deleteById(foundPhoto.getId());
        return "Photo deleted";
    }

    @Override
    public String deletePhotoByNative(Long nativeId) {
        List<Photo> foundPhotos = photoRepository.findByImageUploaderId(nativeId);
        if (foundPhotos == null) return null;
        for (Photo photo : foundPhotos)
            photoRepository.deleteById(photo.getId());
        return "Photos deleted";
    }

    @Override
    public String deletePhotoByTagName(String tagName) {
        List<Photo> foundPhotos = photoRepository.findAll();
        if (foundPhotos.isEmpty()) return null;

        Tag foundTag = tagRepository.findByTagName(tagName).orElseThrow(
                () -> new PhotoAppException("Placeholder")
        );

        for (Photo photo : foundPhotos)
            if (photo.getTags().contains(foundTag))
                photoRepository.deleteById(photo.getId());
        return "Photos deleted";
    }

    @Override
    public String deleteAllPhotos() {
        photoRepository.deleteAll();
        return "All photos cleared";
    }


    //    Helper Methods
    private List<Tag> extractTags(String caption) {
        List<Tag> tags = new ArrayList<>();
        String[] wordsArray = caption.split("[ \\[!@$%^&*()_+=<>{}\\]';:|\".,?]");
        for (String word : wordsArray) {
            if (word.startsWith("#")) {
                String normalisedWord = word
                        .replaceFirst("#", "")
                        .toLowerCase();
                tags.add(Tag.builder()
                        .tagName(normalisedWord).build());
            }
        }
        return tags;
    }

    private Map<?, ?> uploadPhoto(MultipartFile multipartFile, Native aNative) {
        Map<?, ?> uploadResult = new HashMap<>();
        try {
            uploadResult = cloudService.upload(multipartFile.getBytes(), ObjectUtils.asMap(
                    "folder", "photo_app/" + aNative.getCohort().getCohortNumber() + "/" + aNative.getUserName(),
                    "public_id", multipartFile.getOriginalFilename(), "overwrite", true
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadResult;
    }
}
