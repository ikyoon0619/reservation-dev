package com.example.backendspringboot.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    public String getToken(String key, Object value);

    Claims getClamis(String token);
}
