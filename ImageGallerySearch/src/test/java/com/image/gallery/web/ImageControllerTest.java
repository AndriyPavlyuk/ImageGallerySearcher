package com.image.gallery.web;

import com.image.gallery.model.Image;
import com.image.gallery.service.ImageService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    public void shouldGetAllImages() throws Exception {
        //given
        final MockHttpServletRequestBuilder requestBuilder =
                get("/images")
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        final Image image1 = Image.builder().id("1").url("url1").build();
        final Image image2 = Image.builder().id("2").url("url2").build();
        final List<Image> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);

        //when
        when(imageService.getAll()).thenReturn(images);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("@[0]id", Is.is("1")))
                .andExpect(jsonPath("@[0]cropped_picture", Is.is("url1")))
                .andExpect(jsonPath("@[1]id", Is.is("2")))
                .andExpect(jsonPath("@[1]cropped_picture", Is.is("url2")));
    }

    @Test
    public void shouldGetImageByImageId() throws Exception {
        //given
        final MockHttpServletRequestBuilder requestBuilder =
                get("/images/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        //when
        when(imageService.getImage("1")).thenReturn(Image.builder().id("1").url("url").build());

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Is.is("1")))
                .andExpect(jsonPath("cropped_picture", Is.is("url")));
    }
}
