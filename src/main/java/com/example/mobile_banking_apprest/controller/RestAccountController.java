package com.example.mobile_banking_apprest.controller;


import com.example.mobile_banking_apprest.model.Account;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
@RequestMapping("/restAccount/accounts")
public class RestAccountController {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "http://localhost:8080/accounts")
    private String url;

    @GetMapping
    public String getAccounts(Model model) {
        List<Account> accounts = restTemplate.getForObject(url, List.class);
        model.addAttribute("accounts", accounts);

        return "accounts";
    }

    @GetMapping("/showDeleteAccount/{accountId}")
    public String showAccountDelete(@PathVariable("accountId") int accountId, Model model) {
        model.addAttribute("account", restTemplate.getForObject(url + "/account_id/" + accountId, Account.class));
        return "deleteAccount";
    }

    @PostMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable(name = "accountId") int accountId, RedirectAttributes redirectAttributes) {
        restTemplate.delete(url + "/" + accountId);
        redirectAttributes.addFlashAttribute("accountDeleteMessage", "Account was deleted!");
        return "redirect:/restAccount/accounts";
    }

    @GetMapping("/showCreateAccountPage")
    public String showAccountCreate(Model model) {
        model.addAttribute("account", new Account());
        return "createAccount";
    }

    @PostMapping("/create")
    public String createAccount(Account account, RedirectAttributes redirectAttributes) {
        restTemplate.postForEntity(url, account, Account.class).getBody();
        redirectAttributes.addFlashAttribute("accountCreateMessage", "Account was created!");
        return "redirect:/restAccount/accounts";
    }

    @GetMapping("/showUpdateAccount/{accountId}")
    public String showAccountUpdate(@PathVariable("accountId") int accountId, Model model) {
        model.addAttribute("account", restTemplate.getForObject(url + "/account_id/" + accountId, Account.class));

        return "updateAccount";
    }

    @PostMapping("/update/{accountId}")
    public String updateAccount(@ModelAttribute(name = "account") @Valid Account account, @PathVariable int accountId, RedirectAttributes redirectAttributes) {
        restTemplate.patchForObject(url + "/" + accountId, account, Account.class);
        redirectAttributes.addFlashAttribute("accountUpdateMessage", "Account was updated!");

        return "redirect:/restAccount/accounts";
    }

}
