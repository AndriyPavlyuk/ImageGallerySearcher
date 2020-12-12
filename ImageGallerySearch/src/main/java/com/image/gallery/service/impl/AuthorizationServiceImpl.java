package com.image.gallery.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.gallery.model.Credential;
import com.image.gallery.model.Token;
import com.image.gallery.service.AuthorizationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final RestTemplate restTemplate;
    @Value("${auth.apiKey}")
    private String apiKey;
    @Value("${auth.url}")
    private String url;

    private Token token;

    public AuthorizationServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @SneakyThrows
    @Override
    public Token authorise() {
        log.info("[AuthorizationServiceImpl] : authorise triggered");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, "*/*");
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        headers.set(HttpHeaders.CONNECTION, "keep-alive");
        final HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(new Credential(apiKey)), headers);
        final ResponseEntity<Token> tokenResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Token.class);
        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            this.token = tokenResponse.getBody();
            return this.token;
        }
        final SecurityException authException = new SecurityException(
                String.format("Auth error. Status code : %s", tokenResponse.getStatusCode())
        );
        log.error("[AuthorizationServiceImpl] : authorise error", authException);
        throw authException;
    }

    @Override
    public String getToken() {
        log.info("[AuthorizationServiceImpl] : getToken triggered");
        if (token != null) {
            log.info("[AuthorizationServiceImpl] : token is not null");
            return token.getToken();
        }
        log.info("[AuthorizationServiceImpl] : token is null");
        return authorise().getToken();
    }
}
