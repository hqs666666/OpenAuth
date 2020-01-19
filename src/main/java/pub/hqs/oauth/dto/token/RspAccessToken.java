package pub.hqs.oauth.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RspAccessToken{
    private String access_token;
    private String refresh_token;
    private Integer expires_in;
}
