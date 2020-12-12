package com.image.gallery.service;

import com.image.gallery.model.Token;

public interface AuthorizationService {
    Token authorise();

    String getToken();
}
