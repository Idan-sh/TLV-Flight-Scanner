package com.tlvflightscanner.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ApiService {
    @Value("${base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ApiService() {
        restTemplate = new RestTemplate();
    }

    public String fetchDataFromApi() {
        log.info("Fetching flights data from API...");
        String response = restTemplate.getForObject(baseUrl, String.class);
        log.debug("Response from API: {}", response);
        return response;
    }
}
