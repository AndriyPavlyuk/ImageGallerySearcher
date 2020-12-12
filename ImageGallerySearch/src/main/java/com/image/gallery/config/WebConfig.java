package com.image.gallery.config;

import com.image.gallery.config.security.RequestInterceptor;
import com.image.gallery.service.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    RestTemplate restTemplate(AuthorizationService authorizationService) {
        final RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RequestInterceptor(authorizationService));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
