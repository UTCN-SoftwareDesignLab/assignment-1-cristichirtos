package model.DTO;

public class ActivityDTO {

    private Long employeeId;
    private String text;

    public ActivityDTO(Long employeeId, String text) {
        this.employeeId = employeeId;
        this.text = text;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
