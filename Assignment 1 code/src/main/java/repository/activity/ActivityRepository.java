package repository.activity;

import model.DTO.ActivityDTO;
import model.DTO.ReportDTO;
import model.entity.Activity;

import java.util.List;

public interface ActivityRepository {

    boolean createActivity(ActivityDTO activityDTO);

    List<Activity> getActivities(ReportDTO reportDTO);
}
