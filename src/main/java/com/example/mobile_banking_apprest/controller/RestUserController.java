package com.example.mobile_banking_apprest.controller;

import com.example.mobile_banking_apprest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/restUser/users")
public class RestUserController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${users.url}")
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

    @GetMapping("/showDeleteUser/{userId}")
    public String showUserDelete(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", restTemplate.getForObject(url + "/user_id/" + userId, User.class));
        return "deleteUser";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@ModelAttribute(name = "user") @PathVariable int userId) {
        restTemplate.delete(url + "/" + userId);
        return "redirect:/restUser/users";
    }

    @GetMapping("/showUpdateUserPage/{userId}")
    public String showUserUpdate(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", restTemplate.getForObject(url + "/user_id/" + userId, User.class));
        return "updateUser";
    }

    @PostMapping("/{userId}")
    public String updateUser(@ModelAttribute(name = "user") @Valid User user, @PathVariable int userId) {
        restTemplate.patchForObject(url + "/" + userId, user, User.class);

        return "redirect:/restUser/users";
    }
}
