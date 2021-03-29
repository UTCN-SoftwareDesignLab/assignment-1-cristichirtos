package model.entity;

import java.time.LocalDate;

public class Account {

    private Long id;
    private Long clientId;
    private Type type;
    private Long balance;
    private LocalDate creationTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public LocalDate getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDate creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public enum Type {
        CURRENT,
        SAVINGS,
        FIXED_DEPOSIT,
        RECURRING_DEPOSIT
    }
}
