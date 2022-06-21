package com.olatoye.photoapp.utils;

import com.olatoye.photoapp.data.models.Cohort;
import com.olatoye.photoapp.data.models.Native;
import com.olatoye.photoapp.data.models.Photo;
import com.olatoye.photoapp.data.models.Tag;
import com.olatoye.photoapp.dtos.requests.NativeRequestDto;
import com.olatoye.photoapp.dtos.responses.NativeResponseDto;
import com.olatoye.photoapp.dtos.responses.PhotoResponseDto;
import com.olatoye.photoapp.dtos.responses.TagResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.coyote.http11.Constants.a;

@Component
public class ModelMapper {

    public PhotoResponseDto map(Photo photo) {
        return PhotoResponseDto.builder()
                .imageId(photo.getId())
                .imageUploaderId(photo.getImageUploader().getId())
                .imageUrl(photo.getImageUrl())
                .numberOfDownloads(photo.getNumberOfDownloads())
                .dateUploaded(photo.getDateUploaded())
                .build();
    }

    public TagResponseDto map(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }

    public Native map(NativeRequestDto nativeRequestDto) {
        Cohort cohort = Cohort.builder()
                .cohortName(nativeRequestDto.getCohortName())
                .cohortNumber(nativeRequestDto.getCohortNumber())
                .build();

        return Native.builder()
                .firstName(nativeRequestDto.getFirstName())
                .lastName(nativeRequestDto.getLastName())
                .userName(nativeRequestDto.getFirstName())
                .email(nativeRequestDto.getEmail())
                .cohort(cohort)
                .dateCreated(LocalDateTime.now())
                .build();
    }

    public NativeResponseDto map(Native aNative) {
        return NativeResponseDto.builder()
                .id(aNative.getId())
                .firstName(aNative.getFirstName())
                .lastName(aNative.getLastName())
                .userName(aNative.getUserName())
                .email(aNative.getEmail())
                .cohortName(aNative.getCohort().getCohortName())
                .cohortNumber(aNative.getCohort().getCohortNumber())
                .dateCreated(aNative.getDateCreated().toString())
                .build();
    }

    public Native map(NativeResponseDto nativeResponseDto) {
        Cohort cohort = Cohort.builder()
                .cohortName(nativeResponseDto.getCohortName())
                .cohortNumber(nativeResponseDto.getCohortNumber())
                .build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(nativeResponseDto.getDateCreated(), formatter);

        return Native.builder()
                .id(nativeResponseDto.getId())
                .firstName(nativeResponseDto.getFirstName())
                .lastName(nativeResponseDto.getLastName())
                .userName(nativeResponseDto.getUserName())
                .email(nativeResponseDto.getEmail())
                .cohort(cohort)
                .dateCreated(localDateTime)
                .build();
    }
}
