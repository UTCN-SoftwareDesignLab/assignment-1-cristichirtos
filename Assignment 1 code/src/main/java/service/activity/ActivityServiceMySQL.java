package service.activity;

import model.DTO.ActivityDTO;
import model.DTO.ReportDTO;
import model.entity.Activity;
import model.validation.Notification;
import repository.activity.ActivityRepository;

import java.sql.Date;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Notification<String> getActivities(ReportDTO reportDTO) {
        Notification<String> notification = new Notification<>();
        List<Activity> activities = activityRepository.getActivities(reportDTO);
        if (activities.isEmpty()) {
            notification.addError("Employee does not exist or has no activities.");
        } else {
            StringBuilder result = new StringBuilder();
            for (Activity activity : activities) {
                if (Date.valueOf(activity.getTimestamp())
                        .after(Date.valueOf(reportDTO.getStartDate())) &&
                    Date.valueOf(activity.getTimestamp())
                        .before(Date.valueOf(reportDTO.getEndDate()))) {
                    result.append("Activity id: ")
                            .append(activity.getId())
                            .append("\nActivity text: ")
                            .append(activity.getText())
                            .append("\nActivity timestamp: ")
                            .append(activity.getTimestamp().toString())
                            .append("\n\n");
                }
            }
            notification.setResult(result.toString());
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
