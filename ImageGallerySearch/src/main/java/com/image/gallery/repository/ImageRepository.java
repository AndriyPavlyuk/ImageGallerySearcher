package com.image.gallery.repository;

import com.image.gallery.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String> {

    @Query("select i from Image i where i.metaTag LIKE CONCAT('%',:tag,'%')")
    List<ImageEntity> getAllImagesMatchingWithMatchingTag(@Param("tag") String tag);
}
