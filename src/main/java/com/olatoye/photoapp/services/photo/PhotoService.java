package com.olatoye.photoapp.services.photo;

import com.olatoye.photoapp.dtos.requests.PhotoRequest;
import com.olatoye.photoapp.dtos.responses.PhotoResponse;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;

public interface PhotoService {
    PhotoResponse createPhoto(PhotoRequest photoRequest) throws IOException;

    PhotoResponse findPhotoByPhotoId(Long photoId);
    List<PhotoResponse> findPhotoByNative(Long nativeId);
    List<PhotoResponse> findPhotoByTagName(String tagName);
    List<PhotoResponse> findAllPhotos();

    ByteArrayResource downloadPhoto(PhotoResponse photoResponse) throws IOException;

    String deletePhotoByPhotoId(Long photoId);
    String deletePhotoByNative(Long nativeId);
    String deletePhotoByTagName(String tagName);
    String deleteAllPhotos();
}
