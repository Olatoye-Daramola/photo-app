package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByImageUploaderId(Long uploaderId);
}
