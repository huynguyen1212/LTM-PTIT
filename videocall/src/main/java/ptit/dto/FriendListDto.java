package ptit.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FriendListDto {
    private Long id;
    private Date created_at;
    private Date updated_at;
    private Boolean confirmed;
    private Long blocking_id;
    private UserEntity friend;
}
