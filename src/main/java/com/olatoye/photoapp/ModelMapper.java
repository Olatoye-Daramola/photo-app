package com.olatoye.photoapp;

import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.dtos.responses.PhotoResponse;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public PhotoResponse map(Photo photo) {
        return PhotoResponse.builder()
                .imageId(photo.getId())
                .imageUploaderId(photo.getImageUploader().getId())
                .imageUrl(photo.getImageUrl())
                .numberOfDownloads(photo.getNumberOfDownloads())
                .dateUploaded(photo.getDateUploaded())
                .build();
    }
}
