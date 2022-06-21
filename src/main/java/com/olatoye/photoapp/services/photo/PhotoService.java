package com.olatoye.photoapp.services.photo;

import com.olatoye.photoapp.dtos.requests.PhotoRequestDto;
import com.olatoye.photoapp.dtos.responses.PhotoResponseDto;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;

public interface PhotoService {
    PhotoResponseDto createPhoto(PhotoRequestDto photoRequestDto) throws IOException;

    PhotoResponseDto findPhotoByPhotoId(Long photoId);
    List<PhotoResponseDto> findPhotoByNative(Long nativeId);
    List<PhotoResponseDto> findPhotoByNativeName(String firstNameOrLastName);
    List<PhotoResponseDto> findPhotoByTagName(String tagName);
    List<PhotoResponseDto> findAllPhotos();

    ByteArrayResource downloadPhoto(PhotoResponseDto photoResponseDto) throws IOException;

    String deletePhotoByPhotoId(Long photoId);
    String deletePhotoByNative(Long nativeId);
    String deletePhotoByTagName(String tagName);
    String deleteAllPhotos();
}
