package launcher;

import controller.AdministratorController;
import controller.EmployeeController;
import controller.LoginController;
import database.DatabaseConnectionFactory;
import model.Authentication;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {

    private final Authentication authentication;

    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdministratorView administratorView;

    private final LoginController loginController;
    private final EmployeeController employeeController;
    private final AdministratorController administratorController;

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.authentication = new Authentication();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.activityRepository = new ActivityRepositoryMySQL(connection);

        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.accountService = new AccountServiceMySQL(accountRepository, clientRepository);
        this.clientService = new ClientServiceMySQL(clientRepository);
        this.activityService = new ActivityServiceMySQL(activityRepository);

        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.administratorView = new AdministratorView();

        this.loginController = new LoginController(loginView, authenticationService, authentication);
        this.employeeController = new EmployeeController(employeeView, accountService, clientService, activityService, authentication);
        this.administratorController = new AdministratorController(administratorView, authenticationService, activityService);
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public AdministratorView getAdministratorView() {
        return administratorView;
   }
}
