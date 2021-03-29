package model.builder;

import model.entity.Account;

import java.time.LocalDate;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setClientId(Long clientId) {
        account.setClientId(clientId);
        return this;
    }

    public AccountBuilder setType(Account.Type type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(Long balance) {
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setCreationTimestamp(LocalDate creationTimestamp) {
        account.setCreationTimestamp(creationTimestamp);
        return this;
    }

    public Account build() {
        return this.account;
    }
}
