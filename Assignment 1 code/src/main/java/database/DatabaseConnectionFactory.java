package database;

public class DatabaseConnectionFactory {

    public JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper(Constants.Schemas.TEST);
        }
        return new JDBConnectionWrapper(Constants.Schemas.PRODUCTION);
    }
}
