package service;

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
import service.account.AccountService;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;

import java.util.Collections;

public class ActivityServiceMySQLTest {

    private static ActivityService service;
    private static ActivityRepository activityRepository;
    private static Long employeeId;

    @BeforeClass
    public static void setupClass() {
        JDBConnectionWrapper connectionWrapper = new DatabaseConnectionFactory().getConnectionWrapper(true);
        activityRepository = new ActivityRepositoryMySQL(connectionWrapper.getConnection());
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        UserRepository userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
        service = new ActivityServiceMySQL(activityRepository);
        User user = new UserBuilder()
                .setEmail("test@test.com")
                .setPassword("Test123!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(Constants.Roles.EMPLOYEE)))
                .build();
        userRepository.save(user);
        employeeId = userRepository.findByEmail("test@test.com").getResult().getId();
    }

    @Before
    public void setup() {
        activityRepository.removeAll();
    }

    @Test
    public void save() {
        Assert.assertTrue(service.addActivity(new ActivityDTO(employeeId, "some text")).getResult());
    }

    @Test
    public void getActivities() {
        ActivityDTO activityDTO = new ActivityDTO(employeeId, "Some text");
        service.addActivity(activityDTO);
        Assert.assertFalse(service.getActivities(new ReportDTO(employeeId.toString(), "2020-03-20", "2022-05-15")).getResult().isEmpty());
    }
}
