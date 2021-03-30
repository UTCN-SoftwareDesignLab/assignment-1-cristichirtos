package repository.activity;

import model.DTO.ActivityDTO;
import model.entity.Activity;

import java.util.List;

public interface ActivityRepository {

    boolean createActivity(ActivityDTO activityDTO);

    List<Activity> getAllByEmployeeId(Long employeeId);
}
