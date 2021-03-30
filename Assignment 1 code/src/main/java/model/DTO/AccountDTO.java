package model.DTO;

import model.entity.Account;

public class AccountDTO {

    private Long ownerPersonalNumericalCode;
    private Account.Type type;
    private String balance;

    public AccountDTO(Long ownerPersonalNumericalCode, Account.Type type, String balance) {
        this.ownerPersonalNumericalCode = ownerPersonalNumericalCode;
        this.type = type;
        this.balance = balance;
    }

    public Long getOwnerPersonalNumericalCode() {
        return ownerPersonalNumericalCode;
    }

    public void setOwnerPersonalNumericalCode(Long ownerPersonalNumericalCode) {
        this.ownerPersonalNumericalCode = ownerPersonalNumericalCode;
    }

    public Account.Type getType() {
        return type;
    }

    public void setType(Account.Type type) {
        this.type = type;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
