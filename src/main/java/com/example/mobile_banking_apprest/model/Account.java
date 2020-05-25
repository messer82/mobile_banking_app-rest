package com.example.mobile_banking_apprest.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Account {

    private Integer accountId;
    private Integer userId;
    @NotBlank
    private String accountNumber;
    @NotNull
    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(userId, account.userId) &&
                Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId, accountNumber, balance);
    }
}
