package repository.client;

import model.DTO.ClientDTO;
import model.builder.ClientBuilder;
import model.entity.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        String sql = "Select * from " + CLIENT;

        List<Client> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                clients.add(getClientFromResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client findById(Long id) {
        String sql = "Select * from " + CLIENT + " where id = " + id;

        Client foundClient = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                foundClient = getClientFromResultSet(resultSet);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return foundClient;
    }

    @Override
    public Client findByPersonalNumericalCode(Long personalNumericalCode) {
        String sql = "Select * from " + CLIENT + " where personalNumericalCode = " + personalNumericalCode;

        Client foundClient = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                foundClient = getClientFromResultSet(resultSet);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return foundClient;
    }

    @Override
    public boolean save(ClientDTO clientDTO) {
        String sql = "INSERT INTO " + CLIENT + " values (null, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setString(1, clientDTO.getName());
            insertStatement.setString(2, clientDTO.getIdentityCardNumber());
            insertStatement.setLong(3, clientDTO.getPersonalNumericalCode());
            insertStatement.setString(4, clientDTO.getAddress());
            insertStatement.setString(5, clientDTO.getPhoneNumber());
            insertStatement.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE " + CLIENT + " SET name = ?, identityCardNumber = ?, personalNumericalCode = ?, " +
                "address = ?, phoneNumber = ? WHERE id = ?";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdentityCardNumber());
            insertStatement.setLong(3, client.getPersonalNumericalCode());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setString(5, client.getPhoneNumber());
            insertStatement.setLong(6, client.getId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteClient(Long id) {
        String sql = "DELETE from " + CLIENT + " where id = " + id;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from " + CLIENT + " where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdentityCardNumber(rs.getString("identityCardNumber"))
                .setPersonalNumericalCode(rs.getLong("personalNumericalCode"))
                .setAddress(rs.getString("address"))
                .setPhoneNumber(rs.getString("phoneNumber"))
                .setCreationTimestamp(rs.getDate("creationTimestamp").toLocalDate())
                .build();
    }
}
