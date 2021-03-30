package service.client;

import model.DTO.ClientDTO;
import model.entity.Client;
import model.validation.Notification;

public interface ClientService {

    Notification<Boolean> createClient(ClientDTO clientDTO);

    Notification<Boolean> updateClient(ClientDTO clientDTO);

    Notification<Boolean> deleteClientByPersonalNumericalCode(Long clientPNC);

    Notification<Client> findClientByPersonalNumericalCode(Long clientPersonalNumericalCode);

    String getStringFromClient(Client client);
}
