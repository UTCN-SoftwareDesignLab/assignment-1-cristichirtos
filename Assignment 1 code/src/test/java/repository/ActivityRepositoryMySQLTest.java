package repository;

import database.Constants;
import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.DTO.ActivityDTO;
import model.DTO.ReportDTO;
import model.builder.UserBuilder;
import model.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.util.Collections;

public class ActivityRepositoryMySQLTest {
    private static ActivityRepository repository;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        repository = new ActivityRepositoryMySQL(connectionWrapper.getConnection());
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        Assert.assertTrue(userRepository.save(user));
    }

    @Before
    public void setup() {
        repository.removeAll();
    }

    @Test
    public void save() {
        ActivityDTO activityDTO = new ActivityDTO(userRepository.findByEmail("test@test.com").getResult().getId(), "Some text");
        Assert.assertTrue(repository.createActivity(activityDTO));
    }

    @Test
    public void getActivities() {
        Long id = userRepository.findByEmail("test@test.com").getResult().getId();
        ActivityDTO activityDTO = new ActivityDTO(id, "Some text");
        repository.createActivity(activityDTO);
        Assert.assertFalse(repository.getActivities(new ReportDTO(id.toString(), "2020-03-20", "2022-05-15")).isEmpty());
    }

    @Test
    public void removeAll() {
        Long id = userRepository.findByEmail("test@test.com").getResult().getId();
        ActivityDTO activityDTO = new ActivityDTO(id, "Some text");
        repository.createActivity(activityDTO);
        repository.removeAll();
        Assert.assertTrue(repository.getActivities(new ReportDTO(id.toString(), "2020-03-20", "2022-05-15")).isEmpty());

    }
}
