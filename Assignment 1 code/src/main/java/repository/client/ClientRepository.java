package repository;

import model.entity.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id);

    boolean save(Client client);

    void removeAll();
}
