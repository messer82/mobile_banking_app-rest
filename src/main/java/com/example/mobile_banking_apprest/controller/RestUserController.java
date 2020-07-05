package com.example.mobile_banking_apprest.controller;

import com.example.mobile_banking_apprest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/restUser/users")
public class RestUserController {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "http://localhost:8080/mobile_banking_users")
    private String url;

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = restTemplate.getForObject(url, List.class);
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/search")
    public String getUsersByName(@RequestParam(value = "search") String userName, Model model) {
        List<User> searchedUser = restTemplate.getForObject(url + "/" + userName, List.class, userName);
        model.addAttribute("search", searchedUser);
        return "requestedUser";
    }

    @GetMapping("/showCreateUserPage")
    public String showUserCreate(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping(value = "/create")
    public String createUser(User user, RedirectAttributes redirectAttributes) {
        User createdUser = restTemplate.postForEntity(url, user, User.class).getBody();
        redirectAttributes.addFlashAttribute("userCreateMessage", "User was created!");
        return "redirect:/restUser/users";
    }

    @GetMapping("/showDeleteUser/{userId}")
    public String showUserDelete(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", restTemplate.getForObject(url + "/user_id/" + userId, User.class));
        return "deleteUser";
    }

    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable(name = "userId") int userId, RedirectAttributes redirectAttributes) {
        restTemplate.delete(url + "/" + userId);
        redirectAttributes.addFlashAttribute("userDeleteMessage", "User was deleted!");
        return "redirect:/restUser/users";
    }

    @GetMapping("/showUpdateUserPage/{userId}")
    public String showUserUpdate(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", restTemplate.getForObject(url + "/user_id/" + userId, User.class));
        return "updateUser";
    }

    @PostMapping("/{userId}")
    public String updateUser(@ModelAttribute(name = "user") @Valid User user, @PathVariable int userId, RedirectAttributes redirectAttributes) {
        restTemplate.patchForObject(url + "/" + userId, user, User.class);
        redirectAttributes.addFlashAttribute("userUpdateMessage", "User information was updated!");
        return "redirect:/restUser/users";
    }

}
