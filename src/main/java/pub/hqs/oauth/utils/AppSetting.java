package pub.hqs.oauth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppSetting {
    @Value("${custom.wechat.appId}")
    public String appId;
    @Value("${custom.wechat.secret}")
    public String secret;
}
