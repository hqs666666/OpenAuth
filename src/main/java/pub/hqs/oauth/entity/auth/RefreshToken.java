package pub.hqs.oauth.entity.auth;

import lombok.Data;

import java.util.Date;

@Data
public class RefreshToken {
    private String id;
    private String tokenId;
    private String refreshToken;
    private Long expiresIn;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
}
