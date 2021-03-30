package model.validation;

import model.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private static final int IDENTITY_CARD_NUMBER_LENGTH = 8;
    private static final int PHONE_NUMBER_LENGTH = 10;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        this.errors = new ArrayList<>();
    }

    public boolean validate() {
        validatePNC(client.getPersonalNumericalCode());
        validateName(client.getName());
        validateIDC(client.getIdentityCardNumber());
        validateAddress(client.getAddress());
        validatePhoneNumber(client.getPhoneNumber());
        return errors.isEmpty();
    }

    private void validatePNC(Long pnc) {
        if (pnc < 999999999999L || pnc > 9999999999999L) {
            errors.add("Invalid personal numerical code! It must have 13 digits.");
        }
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            errors.add("Invalid name!");
        }
    }

    private void validateIDC(String idc) {
        if (idc.isEmpty() || idc.length() != IDENTITY_CARD_NUMBER_LENGTH) {
            errors.add("Invalid identity card number!");
        }
    }

    private void validateAddress(String address) {
        if (address.isEmpty()) {
            errors.add("Invalid address!");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty() || phoneNumber.length() != PHONE_NUMBER_LENGTH) {
            errors.add("Invalid phone number!");
        }
    }
}
