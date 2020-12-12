package com.image.gallery.web;

import com.image.gallery.exception.ResourceNotFoundException;
import com.image.gallery.model.Image;
import com.image.gallery.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value ="/images",
            produces = {"application/json"},
            method = RequestMethod.GET)
    private ResponseEntity<List<Image>> getImages() {
        log.info("Start searching all images");
        return ResponseEntity.ok(imageService.getAll());
    }

    @RequestMapping(value ="/images/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    private ResponseEntity<Image> getImage(@PathVariable(value = "id") String id) throws ResourceNotFoundException {
        log.info("Start searching image");
        return ResponseEntity.ok(imageService.getImage(id));
    }

    @RequestMapping(value ="/{searchTerm}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    private ResponseEntity<List<Image>> search(@PathVariable String searchTerm) {
        log.info("Start search image by search term");
        return ResponseEntity.ok(imageService.searchImageByTerm(searchTerm));
    }
}

