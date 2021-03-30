package repository.account;

import model.DTO.TransferDTO;
import model.entity.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id);

    Account findByClientId(Long id);

    boolean save(Account account);

    boolean update(Account account);

    boolean transferMoney(TransferDTO transferDTO);

    boolean deleteAccount(Long id);

    boolean deleteByClientId(Long clientId);

    void deleteAll();
}
