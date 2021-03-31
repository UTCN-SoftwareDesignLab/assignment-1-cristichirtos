package repository;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.ClientDTO;
import model.builder.ClientBuilder;
import model.entity.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

public class ClientRepositoryMySQLTest {

    private static ClientRepository repository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        repository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
    }

    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Client> noClients = repository.findAll();
        Assert.assertTrue(noClients.isEmpty());
    }

    @Test
    public void findById() {
        long goodId;
        long badId = -1L;
        String name = "Gigel";
        Assert.assertNull(repository.findById(badId));
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");
        repository.save(clientDTO);
        Assert.assertNull(repository.findById(badId));
        List<Client> clients = repository.findAll();
        goodId = clients.get(0).getId();
        Client foundClient = repository.findById(goodId);
        Assert.assertNotNull(foundClient);
        Assert.assertEquals(foundClient.getName(), name);
    }

    @Test
    public void findByPNC() {
        long goodPNC = 1991125123456L;
        long badPNC = 5L;
        String name = "Gigel";
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");
        repository.save(clientDTO);
        Assert.assertNull(repository.findByPersonalNumericalCode(badPNC));
        Client foundClient = repository.findByPersonalNumericalCode(goodPNC);
        Assert.assertNotNull(foundClient);
        Assert.assertEquals(foundClient.getName(), name);
    }

    @Test
    public void save() {
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");

        Assert.assertTrue(repository.save(clientDTO));
    }

    @Test
    public void updateClient() {
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");
        repository.save(clientDTO);
        Client client = repository.findAll().get(0);
        String phoneNumber = "112";
        String address = "Cloud 9";
        client.setPhoneNumber(phoneNumber);
        client.setAddress(address);
        repository.update(client);
        client = repository.findAll().get(0);
        Assert.assertEquals(phoneNumber, client.getPhoneNumber());
        Assert.assertEquals(address, client.getAddress());
        Assert.assertEquals("Gigel", client.getName());
    }

    @Test
    public void deleteClient() {
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");
        repository.save(clientDTO);
        List<Client> clients = repository.findAll();
        repository.deleteClient(clients.get(0).getId());
        clients = repository.findAll();
        Assert.assertTrue(clients.isEmpty());
    }

    @Test
    public void deleteAll() {
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");
        repository.save(clientDTO);
        repository.deleteAll();
        List<Client> noClients = repository.findAll();
        Assert.assertTrue(noClients.isEmpty());
    }
}
