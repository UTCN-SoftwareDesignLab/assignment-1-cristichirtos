package repository;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.entity.Account;
import model.entity.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

public class AccountRepositoryMySQLTest {

    private static AccountRepository repository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        repository = new AccountRepositoryMySQL(connectionWrapper.getConnection());
        clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
        Client randomClient = new ClientBuilder()
                .setName("Gigel")
                .setIdentityCardNumber("CJ112112")
                .setPersonalNumericalCode(1651021455123L)
                .setAddress("In your heart")
                .setPhoneNumber("0742589216")
                .setCreationTimestamp(LocalDate.now())
                .build();
        clientRepository.save(randomClient);
    }

    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Account> noAccounts = repository.findAll();
        Assert.assertTrue(noAccounts.isEmpty());
    }

    @Test
    public void findById() {
        long goodId;
        long badId = -1L;
        Long balance = 100000L;
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(clientRepository.findAll().get(0).getId())
                .setBalance(balance)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        Assert.assertNull(repository.findById(badId));
        goodId = repository.findAll().get(0).getId();
        Account foundAccount = repository.findById(goodId);
        Assert.assertNotNull(foundAccount);
        Assert.assertEquals(foundAccount.getBalance(), balance);
    }

    @Test
    public void save() {
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(clientRepository.findAll().get(0).getId())
                .setBalance(10000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);

        Assert.assertTrue(repository.save(account));
    }

    @Test
    public void updateClient() {
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(clientRepository.findAll().get(0).getId())
                .setBalance(10000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        account = repository.findAll().get(0);
        Long balance = 15400L;
        Account.Type type = Account.Type.FIXED_DEPOSIT;
        account.setBalance(balance);
        account.setType(type);
        repository.update(account);
        Assert.assertTrue(repository.update(account));
        account = repository.findAll().get(0);
        Assert.assertEquals(balance, account.getBalance());
        Assert.assertEquals(type, account.getType());
    }

    @Test
    public void deleteClient() {
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(clientRepository.findAll().get(0).getId())
                .setBalance(1000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        List<Account> accounts = repository.findAll();
        repository.deleteAccount(accounts.get(0).getId());
        accounts = repository.findAll();
        Assert.assertTrue(accounts.isEmpty());
    }

    @Test
    public void deleteAll() {
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(clientRepository.findAll().get(0).getId())
                .setBalance(10000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        repository.deleteAll();
        List<Account> noAccounts = repository.findAll();
        Assert.assertTrue(noAccounts.isEmpty());
    }
}
