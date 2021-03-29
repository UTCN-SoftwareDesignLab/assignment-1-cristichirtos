package repository.client;

import model.entity.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id);

    boolean update(Client client);

    boolean save(Client client);

    void deleteClient(Long id);

    void deleteAll();
}
