package pub.hqs.oauth.entity.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessToken {
    private String id;
    private String accessToken;
    private String userId;
    private String userName;
    private String clientId;
    private LocalDateTime expiresIn;
    private String grantType;
    private String scope;
    private String createUser;
    private LocalDateTime createTime;
    private String updateUser;
    private LocalDateTime updateTime;
}
