package repository.user;

import model.builder.UserBuilder;
import model.entity.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;

import static database.Constants.Tables.*;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<User> findByEmailAndPassword(String email, String password) {
        Notification<User> findByEmailAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `email`=\'" + email + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setEmail(userResultSet.getString("email"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByEmailAndPasswordNotification.setResult(user);
            } else {
                findByEmailAndPasswordNotification.addError("Invalid email or password!");
            }
            return findByEmailAndPasswordNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            findByEmailAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByEmailAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO " + USER + " values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getEmail());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + USER + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Notification<User> findByEmail(String email) {
        Notification<User> findByEmail = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `email`=\'" + email + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setEmail(userResultSet.getString("email"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByEmail.setResult(user);
            } else {
                findByEmail.addError("Failed to retrieve user.");
            }
            return findByEmail;
        } catch (SQLException e) {
            e.printStackTrace();
            findByEmail.addError("Something is wrong with the Database");
        }
        return findByEmail;
    }

    @Override
    public Boolean update(String email, String password) {
        String sql = "UPDATE " + USER + " SET password = ? WHERE email = ?";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, password);
            insertStatement.setString(2, email);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
        }
    }

    @Override
    public Notification<Boolean> delete(String email) {
        Notification<Boolean> deleteUser = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + USER + "` where `email`=\'" + email + "\'";
            statement.executeUpdate(sql);
            deleteUser.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            deleteUser.addError("Failed to delete user.");
        }
        return deleteUser;
    }
}
