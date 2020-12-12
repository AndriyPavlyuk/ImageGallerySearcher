package com.image.gallery.service;

import com.image.gallery.exception.ResourceNotFoundException;
import com.image.gallery.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getAll();

    Image getImage(String id) throws ResourceNotFoundException;

    List<Image> searchImageByTerm(String searchTerm);

    boolean saveAll(List<Image> list);
}
