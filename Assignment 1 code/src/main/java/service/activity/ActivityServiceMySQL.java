package service.activity;

import model.DTO.ActivityDTO;
import model.entity.Activity;
import model.validation.Notification;
import repository.activity.ActivityRepository;

import java.util.List;

public class ActivityServiceMySQL implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Notification<List<Activity>> getActivitiesForEmployee(Long employeeId) {
        Notification<List<Activity>> notification = new Notification<>();
        List<Activity> activities = activityRepository.getAllByEmployeeId(employeeId);
        if (activities.isEmpty()) {
            notification.addError("Employee does not exist or has no activities.");
        } else {
            notification.setResult(activities);
        }
        return notification;
    }

    @Override
    public Notification<Boolean> addActivity(ActivityDTO activityDTO) {
        Notification<Boolean> notification = new Notification<>();
        if (!activityRepository.createActivity(activityDTO)) {
            notification.addError("Failed to add activity");
        } else {
            notification.setResult(true);
        }
        return notification;
    }
}
