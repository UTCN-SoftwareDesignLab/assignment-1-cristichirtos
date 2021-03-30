package model.validation;

import model.DTO.AccountDTO;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final AccountDTO accountDTO;

    public AccountValidator(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
        this.errors = new ArrayList<>();
    }

    public boolean validate() {
        validateBalance(accountDTO.getBalance());
        return errors.isEmpty();
    }

    private void validateBalance(String balance) {
        if (balance.isEmpty()) {
            errors.add("Invalid balance!");
        }
    }
}
