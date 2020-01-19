package pub.hqs.oauth.entity.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshToken {
    private String id;
    private String tokenId;
    private String refreshToken;
    private LocalDateTime expiresIn;
    private String createUser;
    private LocalDateTime createTime;
    private String updateUser;
    private LocalDateTime updateTime;
}
