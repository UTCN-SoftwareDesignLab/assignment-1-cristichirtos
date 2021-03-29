package model.builder;

import model.entity.Client;
import java.time.LocalDate;

public class ClientBuilder {

    private final Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdentityCardNumber(String identityCardNumber) {
        client.setIdentityCardNumber(identityCardNumber);
        return this;
    }

    public ClientBuilder setPersonalNumericalCode(Long personalNumericalCode) {
        client.setPersonalNumericalCode(personalNumericalCode);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setPhoneNumber(String phoneNumber) {
        client.setPhoneNumber(phoneNumber);
        return this;
    }

    public ClientBuilder setCreationTimestamp(LocalDate creationTimestamp) {
        client.setCreationTimestamp(creationTimestamp);
        return this;
    }

    public Client build() {
        return this.client;
    }
}
