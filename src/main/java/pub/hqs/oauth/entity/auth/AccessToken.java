package pub.hqs.oauth.entity.auth;

import lombok.Data;

import java.util.Date;

@Data
public class AccessToken {
    private String id;
    private String accessToken;
    private String userId;
    private String userName;
    private String clientId;
    private Long expiresIn;
    private String grantType;
    private String scope;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
}
