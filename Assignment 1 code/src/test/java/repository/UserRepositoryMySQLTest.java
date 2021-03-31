package repository;

import database.Constants;
import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.ClientDTO;
import model.builder.UserBuilder;
import model.entity.Client;
import model.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.util.Collections;
import java.util.List;

public class UserRepositoryMySQLTest {

    private static UserRepository repository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        repository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
    }

    @Before
    public void setup() {
        repository.removeAll();
    }

    @Test
    public void save() {
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        Assert.assertTrue(repository.save(user));
    }

    @Test
    public void findByEmailAndPass() {
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        repository.save(user);
        Assert.assertNotNull(repository.findByEmailAndPassword("test@test.com", "Test123!").getResult());
    }

    @Test
    public void findByEmail() {
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        repository.save(user);
        Assert.assertNotNull(repository.findByEmail("test@test.com").getResult());
    }

    @Test
    public void updateUser() {
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        repository.save(user);
        Assert.assertTrue(repository.update("test@test.com", "Test1234!"));
        Assert.assertNotNull(repository.findByEmailAndPassword("test@test.com", "Test1234!").getResult());
    }

    @Test
    public void deleteUser() {
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        repository.save(user);
        Assert.assertTrue(repository.delete("test@test.com").getResult());
    }

    @Test
    public void deleteAll() {
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        repository.save(user);
        repository.removeAll();
        Assert.assertTrue(repository.findByEmail("test@test.com").hasErrors());
    }
}
