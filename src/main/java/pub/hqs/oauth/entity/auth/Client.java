package pub.hqs.oauth.entity.auth;

import lombok.Data;

import java.util.Date;

@Data
public class Client {
    private String id;
    private String clientId;
    private String clientName;
    private String clientSecret;
    private String redirectUri;
    private String description;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private Integer status;
}
