package pub.hqs.oauth.dto;

import lombok.Data;

@Data
public class ClientInfo {
    private String clientId;
    private String clientName;
    private String description;
    private AuthorizationInfo authorizationInfo;
}
