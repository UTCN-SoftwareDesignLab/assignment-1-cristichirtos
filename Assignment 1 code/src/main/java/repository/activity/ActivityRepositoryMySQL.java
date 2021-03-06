package repository.activity;

import model.DTO.ActivityDTO;
import model.DTO.ReportDTO;
import model.builder.ActivityBuilder;
import model.entity.Activity;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class ActivityRepositoryMySQL implements ActivityRepository {

    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createActivity(ActivityDTO activityDTO) {
        String sql = "INSERT INTO " + ACTIVITY + " values (null, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement(sql);
            insertStatement.setLong(1, activityDTO.getEmployeeId());
            insertStatement.setString(2, activityDTO.getText());
            insertStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Activity> getActivities(ReportDTO reportDTO) {
        String sql = "Select * from " + ACTIVITY + " WHERE employeeId = \'" + reportDTO.getEmployeeId() + "\'";

        List<Activity> activitiesByEmployee = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Activity activity = new ActivityBuilder()
                        .setId(resultSet.getLong("id"))
                        .setEmployeeId(resultSet.getLong("employeeId"))
                        .setText(resultSet.getString("text"))
                        .setTimestamp(resultSet.getDate("timestamp").toLocalDate())
                        .build();
                activitiesByEmployee.add(activity);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return activitiesByEmployee;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE from " + ACTIVITY + " where id >= 0";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
