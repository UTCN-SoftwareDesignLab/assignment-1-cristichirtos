package service.client;

import model.DTO.ClientDTO;
import model.builder.ClientBuilder;
import model.entity.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.client.ClientRepository;

import java.time.LocalDate;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Client> findClientByPersonalNumericalCode(Long clientPersonalNumericalCode) {
        Notification<Client> notification = new Notification<>();
        Client client = clientRepository.findByPersonalNumericalCode(clientPersonalNumericalCode);
        if (client == null) {
            notification.addError("Failed to retrieve client");
        }
        else {
            notification.setResult(client);
        }
        return notification;
    }

    @Override
    public Notification<Boolean> createClient(ClientDTO clientDTO) {
        Notification<Boolean> notification = new Notification<>();
        Client client = new ClientBuilder()
                .setName(clientDTO.getName())
                .setPersonalNumericalCode(clientDTO.getPersonalNumericalCode())
                .setIdentityCardNumber(clientDTO.getIdentityCardNumber())
                .setAddress(clientDTO.getAddress())
                .setPhoneNumber(clientDTO.getPhoneNumber())
                .setCreationTimestamp(LocalDate.now())
                .build();
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();

        if (!clientValid) {
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        } else {
            if (!clientRepository.save(clientDTO)) {
                notification.addError("Failed to create client. It might already exist.");
            } else {
                notification.setResult(true);
            }
        }
        return notification;
    }

    @Override
    public Notification<Boolean> updateClient(ClientDTO clientDTO) {
        Notification<Boolean> notification = new Notification<>();
        Client client = clientRepository.findByPersonalNumericalCode(clientDTO.getPersonalNumericalCode());
        client.setAddress(clientDTO.getAddress());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setName(clientDTO.getName());
        client.setIdentityCardNumber(clientDTO.getIdentityCardNumber());

        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();

        if (!clientValid) {
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        } else {
            notification.setResult(clientRepository.update(client));
        }
        return notification;
    }

    @Override
    public Notification<Boolean> deleteClientByPersonalNumericalCode(Long clientPNC) {
        Notification<Boolean> notification = new Notification<>();
        Client client = clientRepository.findByPersonalNumericalCode(clientPNC);
        if (client == null) {
            notification.addError("Failed to retrieve client");
        }
        else {
            if (!clientRepository.deleteClient(client.getId())) {
                notification.addError("Failed to delete client");
            }
            else {
                notification.setResult(true);
            }
        }
        return notification;
    }

    @Override
    public String getStringFromClient(Client client) {
        return ("Client id: " + client.getId() +
                "\nClient name: " + client.getName() +
                "\nClient personal numerical code: " + client.getPersonalNumericalCode() +
                "\nClient identity card number: " + client.getIdentityCardNumber() +
                "\nClient address: " + client.getAddress() +
                "\nClient phone number: " + client.getPhoneNumber() +
                "\nClient creation date: " + client.getCreationTimestamp().toString());
    }
}
