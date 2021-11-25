package dto;

import java.io.Serializable;

public class StatusDTO implements Serializable{
    private Long userId;
    private int status;

    public StatusDTO() {
    }

    public StatusDTO(Long userId, int status) {
        this.userId = userId;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
