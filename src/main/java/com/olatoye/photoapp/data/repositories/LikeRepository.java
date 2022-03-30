package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPhotoId(Long photoId);
    List<Like> findByLikerId(Long photoId);
}
