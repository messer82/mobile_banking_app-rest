package com.example.mobile_banking_apprest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/")
public class RestAppController {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "http://localhost:8080")
    private String url;

    @GetMapping
    public String showHomePage() {
        return "home";
    }
}
