package com.example.mobile_banking_apprest.controller;

import com.example.mobile_banking_apprest.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/restTransaction/transactions")
public class RestTransactionController {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "http://localhost:8080/transactions")
    private String url;

    @GetMapping
    public String getTransactions(Model model) {
        List<Transaction> transactions = restTemplate.getForObject(url, List.class);
        model.addAttribute("transactions", transactions);

        return "transactions";
    }

    @GetMapping("/showDeleteTransaction/{transactionId}")
    public String showDeleteTransaction(@PathVariable("transactionId") int transactionId, Model model) {
        model.addAttribute("transaction", restTemplate.getForObject(url + "/transaction_id/" + transactionId, Transaction.class));

        return "deleteTransaction";
    }

    @PostMapping("/delete/{transactionId}")
    public String deleteTransaction(@PathVariable("transactionId") int transactionId, RedirectAttributes redirectAttributes) {
        restTemplate.delete(url + "/" + transactionId);
        redirectAttributes.addFlashAttribute("deleteTransactionMessage", "Transaction was deleted!");

        return "redirect:/restTransaction/transactions";
    }

    @GetMapping("/showCreateTransactionPage")
    public String showTransactionCreate(Model model) {
        model.addAttribute("transaction", new Transaction());

        return "createTransaction";
    }

    @PostMapping("/create")
    public String makeTransaction(Transaction transaction, RedirectAttributes redirectAttributes) {
        restTemplate.postForEntity(url, transaction,Transaction.class);
        redirectAttributes.addFlashAttribute("createTransactionMessage", "Transaction was made!");

        return "redirect:/restTransaction/transactions";
    }

    @GetMapping("/transactionsByAccount/{accountId}")
    public String transactionsByAccountId(@PathVariable("accountId") int accountId, Model model) {
        List<Transaction> transactionsByAccount = restTemplate.getForObject(url + "/" + accountId, List.class);
        model.addAttribute("transactionsByAccount", transactionsByAccount);

        return "transactionsByAccount";
    }
}
