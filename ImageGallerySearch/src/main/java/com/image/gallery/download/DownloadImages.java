package com.image.gallery.download;


import com.image.gallery.model.Image;
import com.image.gallery.model.Page;
import com.image.gallery.service.FileAnalyzerService;
import com.image.gallery.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DownloadImages {

    private final ImageService imageService;
    private final FileAnalyzerService fileAnalyzerService;
    private final RestTemplate restTemplate;
    @Value("${sync.url}")
    private String url;

    public DownloadImages(ImageService imageService, FileAnalyzerService fileAnalyzerService, RestTemplate restTemplate) {
        this.imageService = imageService;
        this.fileAnalyzerService = fileAnalyzerService;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${sync.time}")
    public void scheduledMethod() {
        boolean hasMore = true;
        int page = 1;
        while (hasMore) {
            final ResponseEntity<Page> response = restTemplate.exchange(url + "?page=" + page, HttpMethod.GET, null, Page.class);
            final Page responseBody = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && responseBody != null) {
                final List<Image> images = fileAnalyzerService.interceptImageListWithMeta(responseBody.getPictures(), page);
                imageService.saveAll(images);
                hasMore = responseBody.getHasMore();
                page++;
            } else {
                hasMore = false;
                page = 1;
            }
        }
    }
}
