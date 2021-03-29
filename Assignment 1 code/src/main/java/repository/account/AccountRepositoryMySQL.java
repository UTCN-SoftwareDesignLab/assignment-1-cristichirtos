package repository.account;

import database.JDBConnectionWrapper;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.entity.Account;
import model.entity.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        String sql = "Select * from " + ACCOUNT;

        List<Account> accounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account findById(Long id) {
        String sql = "Select * from " + ACCOUNT + " where id = " + id;

        Account foundAccount = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                foundAccount = getAccountFromResultSet(resultSet);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return foundAccount;
    }

    @Override
    public Account findByClientId(Long clientId) {
        String sql = "Select * from " + ACCOUNT + " where clientId = " + clientId;

        Account foundAccount = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                foundAccount = getAccountFromResultSet(resultSet);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return foundAccount;
    }

    @Override
    public boolean save(Account account) {
        String sql = "INSERT INTO " + ACCOUNT + " values (null, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setLong(1, account.getClientId());
            insertStatement.setString(2, account.getType().name());
            insertStatement.setLong(3, account.getBalance());
            insertStatement.setDate(4, new java.sql.Date(account.getCreationTimestamp().toEpochDay()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE " + ACCOUNT + " SET balance = ?, type = ? WHERE id = ?";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setLong(1, account.getBalance());
            insertStatement.setString(2, account.getType().name());
            insertStatement.setLong(3, account.getId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean transferMoney(Long idSenderAccount, Long idReceiverAccount, Long amount) {
        String sql = "UPDATE " + ACCOUNT + " SET balance = ( case when id = ? then balance - ? when id = ? then balance + ? end) " +
                "WHERE id in (?, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setLong(1, idSenderAccount);
            insertStatement.setLong(2, amount);
            insertStatement.setLong(3, idReceiverAccount);
            insertStatement.setLong(4, amount);
            insertStatement.setLong(5, idSenderAccount);
            insertStatement.setLong(6, idReceiverAccount);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void deleteAccount(Long id) {
        String sql = "DELETE from " + ACCOUNT + " where id = " + id;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from " + ACCOUNT + " where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setClientId(rs.getLong("clientId"))
                .setType(Account.Type.valueOf(rs.getString("type")))
                .setBalance(rs.getLong("balance"))
                .setCreationTimestamp(LocalDate.ofEpochDay(rs.getDate("creationTimestamp").getTime()))
                .build();
    }
}
