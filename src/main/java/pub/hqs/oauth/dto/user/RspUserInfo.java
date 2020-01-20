package pub.hqs.oauth.dto.user;

import lombok.Data;

@Data
public class RspUserInfo {
    private String id;
    private String username;
    private String mobile;
    private String email;
}
