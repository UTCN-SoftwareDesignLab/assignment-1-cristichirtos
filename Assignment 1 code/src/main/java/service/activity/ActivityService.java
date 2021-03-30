package service.activity;

import model.DTO.ActivityDTO;
import model.entity.Activity;
import model.validation.Notification;

import java.util.List;

public interface ActivityService {

    Notification<List<Activity>> getActivitiesForEmployee(Long employeeId);

    Notification<Boolean> addActivity(ActivityDTO activityDTO);
}
