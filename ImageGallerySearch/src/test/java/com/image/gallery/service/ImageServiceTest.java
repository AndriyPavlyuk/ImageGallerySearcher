package com.image.gallery.service;

import com.image.gallery.entity.ImageEntity;
import com.image.gallery.exception.ResourceNotFoundException;
import com.image.gallery.repository.ImageRepository;
import com.image.gallery.service.impl.ImageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageServiceImpl.class)
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @MockBean
    private ImageRepository imageRepository;

    @Test
    public void getAllImages() {
        // given
        final ImageEntity image1 = ImageEntity.builder().id("1").url("url1").metaTag("metaTag1").build();
        final ImageEntity image2 = ImageEntity.builder().id("2").url("url2").metaTag("metaTag2").build();
        final List<ImageEntity> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);

        // when
        when(imageRepository.findAll()).thenReturn(images);

        // then
        assertEquals("1", imageService.getAll().get(0).getId());
    }

    @Test
    public void getImageById() throws ResourceNotFoundException {
        // given
        final ImageEntity image = ImageEntity.builder().id("1").url("url1").metaTag("metaTag1").build();

        // when
        when(imageRepository.findById("1")).thenReturn(Optional.of(image));

        // then
        assertEquals("1", imageService.getImage("1").getId());
    }
}
