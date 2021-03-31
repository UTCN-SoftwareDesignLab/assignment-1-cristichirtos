package service;

import database.Constants;
import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.CredentialsDTO;
import model.builder.UserBuilder;
import model.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.util.Collections;

public class UserServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    }

    @Before
    public void setup() {
        userRepository.removeAll();
    }

    @Test
    public void register() {
        Assert.assertTrue(authenticationService.register(new CredentialsDTO("test@test.com", "Test123!")).getResult());
    }

    @Test
    public void login() {
        CredentialsDTO credentialsDTO = new CredentialsDTO("test@test.com", "Test123!");
        authenticationService.register(credentialsDTO);
        Assert.assertNotNull(authenticationService.login(credentialsDTO).getResult());
    }

    @Test
    public void findByEmail() {
        authenticationService.register(new CredentialsDTO("test@test.com", "Test123!"));
        Assert.assertNotNull(authenticationService.findUserByEmail("test@test.com").getResult());
    }

    @Test
    public void updateUser() {
        CredentialsDTO credentialsDTO = new CredentialsDTO("test@test.com", "Test123!");
        authenticationService.register(credentialsDTO);
        credentialsDTO.setPassword("Test1234!");
        Assert.assertTrue(authenticationService.updateUser(credentialsDTO).getResult());
        Assert.assertNotNull(authenticationService.login(credentialsDTO).getResult());
    }

    @Test
    public void deleteUser() {
        authenticationService.register(new CredentialsDTO("test@test.com", "Test123!"));
        Assert.assertTrue(authenticationService.deleteUser("test@test.com").getResult());
    }
}
