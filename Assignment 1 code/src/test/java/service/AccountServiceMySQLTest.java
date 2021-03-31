package service;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.AccountDTO;
import model.DTO.BillDTO;
import model.DTO.ClientDTO;
import model.DTO.TransferDTO;
import model.builder.AccountBuilder;
import model.entity.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;

import java.time.LocalDate;
import java.util.List;

public class AccountServiceMySQLTest {

    private static AccountService service;
    private static AccountRepository accountRepository;
    private static final Long PNC = 1991125123456L;
    private static final Long receiverPNC = 1881125123456L;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        ClientRepository clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
        accountRepository = new AccountRepositoryMySQL(connectionWrapper.getConnection());
        service = new AccountServiceMySQL(accountRepository, clientRepository);
        ClientDTO clientDTO = new ClientDTO("Gigel", PNC, "CJ112112", "here", "0745378559");
        ClientDTO clientDTO2 = new ClientDTO("Gigel", receiverPNC, "CJ112112", "here", "0745378559");
        clientRepository.save(clientDTO);
        clientRepository.save(clientDTO2);
    }

    @Before
    public void setup() {
        accountRepository.deleteAll();
    }

    @Test
    public void createAccount() {
        Assert.assertTrue(service.createAccount(new AccountDTO(PNC, Account.Type.CURRENT, "66")).getResult());
    }

    @Test
    public void getAccountByPNC() {
        service.createAccount(new AccountDTO(PNC, Account.Type.CURRENT, "66"));
        Assert.assertNotNull(service.getAccountByClientPersonalNumericalCode(PNC).getResult());
    }

    @Test
    public void updateAccount() {
        AccountDTO accountDTO = new AccountDTO(PNC, Account.Type.CURRENT, "66");
        service.createAccount(accountDTO);
        accountDTO.setBalance("50");
        Assert.assertTrue(service.updateAccount(accountDTO).getResult());
    }

    @Test
    public void deleteByPNC() {
        AccountDTO accountDTO = new AccountDTO(PNC, Account.Type.CURRENT, "66");
        service.createAccount(accountDTO);
        Assert.assertTrue(service.deleteAccountByClientPersonalNumericalCode(PNC).getResult());
    }

    @Test
    public void transferMoney() {
        service.createAccount(new AccountDTO(PNC, Account.Type.CURRENT, "1000"));
        service.createAccount(new AccountDTO(receiverPNC, Account.Type.CURRENT, "2000"));
        Assert.assertTrue(service.transferMoney(new TransferDTO("500", PNC.toString(), receiverPNC.toString())).getResult());
    }

    @Test
    public void processBill() {
        service.createAccount(new AccountDTO(PNC, Account.Type.CURRENT, "1000"));
        Assert.assertTrue(service.processBill(new BillDTO(PNC.toString(), "500")).getResult());
    }
}
