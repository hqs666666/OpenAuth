package pub.hqs.oauth.dto.wxopen;

import lombok.Data;

@Data
public class SessionBag {
    private String openId;
    private String sessionKey;
    private String unionId;
}
