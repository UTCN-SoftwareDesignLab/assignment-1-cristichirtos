package service;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.ClientDTO;
import model.entity.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;

import java.util.List;

public class ClientServiceMySQLTest {

    private static ClientRepository repository;
    private static ClientService service;
    private static final Long PNC = 1991125123456L;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        repository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
        service = new ClientServiceMySQL(repository);
    }

    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void createClient() {
        ClientDTO clientDTO = new ClientDTO("Gigel", PNC, "CJ112112", "here", "0745378559");
        Assert.assertTrue(service.createClient(clientDTO).getResult());
    }

    @Test
    public void findByPNC() {
        ClientDTO clientDTO = new ClientDTO("Gigel", PNC, "CJ112112", "here", "0745378559");
        service.createClient(clientDTO);
        Assert.assertNotNull(service.findClientByPersonalNumericalCode(PNC).getResult());
    }

    @Test
    public void updateClient() {
        ClientDTO clientDTO = new ClientDTO("Gigel", PNC, "CJ112112", "here", "0745378559");
        service.createClient(clientDTO);
        clientDTO.setName("Mihai");
        Assert.assertTrue(service.updateClient(clientDTO).getResult());
    }

    @Test
    public void deleteClient() {
        ClientDTO clientDTO = new ClientDTO("Gigel", PNC, "CJ112112", "here", "0745378559");
        service.createClient(clientDTO);
        Assert.assertTrue(service.deleteClientByPersonalNumericalCode(PNC).getResult());
    }
}
