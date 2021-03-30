package model;

public class Authentication {

    private Long currentUserId;

    public Authentication() {
        this.currentUserId = -1L;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }
}
