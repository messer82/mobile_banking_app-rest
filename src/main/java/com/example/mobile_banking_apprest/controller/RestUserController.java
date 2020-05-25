package com.example.mobile_banking_apprest.controller;

import com.example.mobile_banking_apprest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/restUser/users")
public class RestUserController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("http://localhost:8081/users")
    private String url;

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = restTemplate.getForObject(url, List.class);
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return restTemplate.postForEntity(url, user, User.class).getBody();
    }

    @DeleteMapping("/userId")
    public void deleteUser(@PathVariable int userId) {
        restTemplate.delete(url, userId);
    }
}
