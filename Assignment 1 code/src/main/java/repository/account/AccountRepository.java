package repository.account;

import model.entity.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id);

    Account findByClientId(Long id);

    boolean save(Account account);

    boolean update(Account account);

    boolean transferMoney(Long idSenderAccount, Long idReceiverAccount, Long amount);

    void deleteAccount(Long id);

    void deleteAll();
}
