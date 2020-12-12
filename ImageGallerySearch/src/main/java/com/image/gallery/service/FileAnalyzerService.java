package com.image.gallery.service;

import com.image.gallery.model.Image;

import java.util.List;

public interface FileAnalyzerService {

    List<Image> interceptImageListWithMeta(List<Image> imageList, int page);
}

