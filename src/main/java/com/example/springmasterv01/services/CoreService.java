package com.example.springmasterv01.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CoreService {
    @Value("${token.url}")
    public   String url;

    @Value("${token.client-id}")
    public   String clientId;
}
