package model.builder;

import model.entity.Activity;

import java.time.LocalDate;

public class ActivityBuilder {

    private final Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity();
    }

    public ActivityBuilder setId(Long id) {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setEmployeeId(Long employeeId) {
        activity.setEmployeeId(employeeId);
        return this;
    }

    public ActivityBuilder setText(String text) {
        activity.setText(text);
        return this;
    }

    public ActivityBuilder setTimestamp(LocalDate timestamp) {
        activity.setTimestamp(timestamp);
        return this;
    }

    public Activity build() {
        return this.activity;
    }
}
