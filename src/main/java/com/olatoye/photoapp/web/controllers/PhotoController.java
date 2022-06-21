package com.olatoye.photoapp.web.controllers;

import com.olatoye.photoapp.dtos.requests.PhotoRequestDto;
import com.olatoye.photoapp.dtos.responses.PhotoResponseDto;
import com.olatoye.photoapp.services.photo.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("")
    public ResponseEntity<?> uploadPhoto(@RequestBody PhotoRequestDto photoRequestDto) {
        try {
            PhotoResponseDto photoResponseDto = photoService.createPhoto(photoRequestDto);
            return ResponseEntity.status(CREATED).body(photoResponseDto);
        } catch (IOException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<?> findPhotoByPhotoId(@PathVariable("photoId") Long photoId) {
        try {
            PhotoResponseDto photoResponseDto = photoService.findPhotoByPhotoId(photoId);
            return ResponseEntity.status(FOUND).body(photoResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{nativeId}")
    public ResponseEntity<?> findPhotoByUploader(@PathVariable("nativeId") Long nativeId) {
        try {
            List<PhotoResponseDto> photoResponseDto = photoService.findPhotoByNative(nativeId);
            return ResponseEntity.status(FOUND).body(photoResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{tagName}")
    public ResponseEntity<?> findPhotoByTag(@PathVariable("tagName") String tagName) {
        try {
            List<PhotoResponseDto> photoResponseDto = photoService.findPhotoByTagName(tagName);
            return ResponseEntity.status(FOUND).body(photoResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> findPhotoByNativeName(@PathVariable("name") String name) {
        try {
            List<PhotoResponseDto> photoResponseDto = photoService.findPhotoByNativeName(name);
            return ResponseEntity.status(FOUND).body(photoResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAllPhotos() {
        //        TODO: Make pageable
        try {
            List<PhotoResponseDto> photoResponseDto = photoService.findAllPhotos();
            return ResponseEntity.status(FOUND).body(photoResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadPhoto (@RequestBody PhotoResponseDto photoResponseDto) {
        try {
            Resource resource = photoService.downloadPhoto(photoResponseDto);
            return ResponseEntity.status(OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                            + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<?> deletePhotoById(@PathVariable("photoId") Long photoId) {
        try {
            String response = photoService.deletePhotoByPhotoId(photoId);
            return ResponseEntity.status(OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{nativeId}")
    public ResponseEntity<?> deletePhotoByNative(@PathVariable("nativeId") Long nativeId) {
        try {
            String response = photoService.deletePhotoByNative(nativeId);
            return ResponseEntity.status(OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{tagName}")
    public ResponseEntity<?> deletePhotoByTag(@PathVariable("tagName") String tagName) {
        try {
            String response = photoService.deletePhotoByTagName(tagName);
            return ResponseEntity.status(OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllPhotos() {
        try {
            String response = photoService.deleteAllPhotos();
            return ResponseEntity.status(OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
}
