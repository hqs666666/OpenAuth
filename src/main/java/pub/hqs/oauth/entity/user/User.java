package pub.hqs.oauth.entity.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
}
