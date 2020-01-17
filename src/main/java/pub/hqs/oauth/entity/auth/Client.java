package pub.hqs.oauth.entity.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Client {
    private String id;
    private String clientId;
    private String clientName;
    private String clientSecret;
    private String redirectUri;
    private String description;
    private String createUser;
    private LocalDateTime createTime;
    private String updateUser;
    private LocalDateTime updateTime;
    private Integer status;
}
