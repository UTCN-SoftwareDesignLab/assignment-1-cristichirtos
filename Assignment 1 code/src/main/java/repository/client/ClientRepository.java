package repository.client;

import model.DTO.ClientDTO;
import model.entity.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id);

    Client findByPersonalNumericalCode(Long personalNumericalCode);

    boolean update(Client client);

    boolean save(ClientDTO clientDTO);

    boolean deleteClient(Long id);

    void deleteAll();
}
