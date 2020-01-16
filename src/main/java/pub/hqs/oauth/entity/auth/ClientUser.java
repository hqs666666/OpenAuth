package pub.hqs.oauth.entity.auth;

import lombok.Data;

@Data
public class ClientUser {
    private String id;
    private String clientId;
    private String userId;
    private String scopeId;
}
