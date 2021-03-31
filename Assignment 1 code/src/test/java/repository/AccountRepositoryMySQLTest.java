package repository;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.ClientDTO;
import model.DTO.TransferDTO;
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
        ClientDTO clientDTO = new ClientDTO("Gigel", 1991125123456L, "CJ112112", "here", "0745378559");
        clientRepository.save(clientDTO);
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
    public void findByClientId() {
        Long id = clientRepository.findAll().get(0).getId();
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(id)
                .setBalance(1000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        Assert.assertNotNull(repository.findByClientId(id));
    }

    @Test
    public void transferMoney() {
        ClientDTO clientDTO = new ClientDTO("Gigelus", 1881125123456L, "CJ112112", "here", "0745378559");
        clientRepository.save(clientDTO);
        Long senderPNC = 1991125123456L;
        Long receiverPNC = 1881125123456L;
        Long senderId = clientRepository.findByPersonalNumericalCode(senderPNC).getId();
        Long receiverId = clientRepository.findByPersonalNumericalCode(receiverPNC).getId();
        Long amount = 500L;
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(senderId)
                .setBalance(1000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        Account accountReceiver = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(receiverId)
                .setBalance(2000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(accountReceiver);
        TransferDTO transferDTO = new TransferDTO(amount.toString(), senderPNC.toString(), receiverPNC.toString());
        transferDTO.setSenderAccountId(senderId);
        transferDTO.setReceiverAccountId(receiverId);
        Assert.assertTrue(repository.transferMoney(transferDTO));
    }

    @Test
    public void deleteByClientId() {
        Long id = clientRepository.findAll().get(0).getId();
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(id)
                .setBalance(1000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        repository.save(account);
        Assert.assertTrue(repository.deleteByClientId(id));
    }

    @Test
    public void save() {
        Account account = new AccountBuilder()
                .setType(Account.Type.CURRENT)
                .setClientId(clientRepository.findAll().get(0).getId())
                .setBalance(10000L)
                .setCreationTimestamp(LocalDate.now())
                .build();
        Assert.assertTrue(repository.save(account));
    }

    @Test
    public void updateAccount() {
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
    public void deleteAccount() {
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
