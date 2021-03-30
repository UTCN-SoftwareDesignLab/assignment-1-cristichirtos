package service.activity;

import model.DTO.ActivityDTO;
import model.DTO.ReportDTO;
import model.entity.Activity;
import model.validation.Notification;

import java.util.List;

public interface ActivityService {

    Notification<Boolean> addActivity(ActivityDTO activityDTO);

    Notification<String> getActivities(ReportDTO reportDTO);
}
