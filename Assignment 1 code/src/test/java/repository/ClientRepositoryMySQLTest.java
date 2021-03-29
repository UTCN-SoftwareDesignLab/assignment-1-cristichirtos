package repository;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
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
        Client randomClient = new ClientBuilder()
                .setName(name)
                .setIdentityCardNumber("CJ112112")
                .setPersonalNumericalCode(1651021455123L)
                .setAddress("In your heart")
                .setPhoneNumber("0742589216")
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(randomClient);
        Assert.assertNull(repository.findById(badId));
        List<Client> clients = repository.findAll();
        goodId = clients.get(0).getId();
        Client foundClient = repository.findById(goodId);
        Assert.assertNotNull(foundClient);
        Assert.assertEquals(foundClient.getName(), name);
    }

    @Test
    public void save() {
        Client clientNoName = new ClientBuilder()
                .setAddress("nowhere")
                .setPersonalNumericalCode(45L)
                .setCreationTimestamp(LocalDate.now())
                .build();

        Assert.assertFalse(repository.save(clientNoName));

        Client clientNoPhoneNumber = new ClientBuilder()
                .setName("name")
                .setPersonalNumericalCode(45L)
                .setCreationTimestamp(LocalDate.now())
                .build();

        Assert.assertFalse(repository.save(clientNoPhoneNumber));

        Client randomClient = new ClientBuilder()
                .setName("Gigel")
                .setIdentityCardNumber("CJ112112")
                .setPersonalNumericalCode(1651021455123L)
                .setAddress("In your heart")
                .setPhoneNumber("0742589216")
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(randomClient);

        Assert.assertTrue(repository.save(randomClient));
    }

    @Test
    public void updateClient() {
        repository.save(new ClientBuilder()
                .setName("Gigel")
                .setIdentityCardNumber("CJ112112")
                .setPersonalNumericalCode(1651021455123L)
                .setAddress("In your heart")
                .setPhoneNumber("0742589216")
                .setCreationTimestamp(LocalDate.now())
                .build());
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
        repository.save(new ClientBuilder()
                .setName("Gigel")
                .setIdentityCardNumber("CJ112112")
                .setPersonalNumericalCode(1651021455123L)
                .setAddress("In your heart")
                .setPhoneNumber("0742589216")
                .setCreationTimestamp(LocalDate.now())
                .build());
        List<Client> clients = repository.findAll();
        repository.deleteClient(clients.get(0).getId());
        clients = repository.findAll();
        Assert.assertTrue(clients.isEmpty());
    }

    @Test
    public void deleteAll() {
        repository.save(new ClientBuilder()
                .setName("Gigel")
                .setIdentityCardNumber("CJ112112")
                .setPersonalNumericalCode(1651021455123L)
                .setAddress("In your heart")
                .setPhoneNumber("0742589216")
                .setCreationTimestamp(LocalDate.now())
                .build());
        repository.deleteAll();
        List<Client> noClients = repository.findAll();
        Assert.assertTrue(noClients.isEmpty());
    }
}
