package pub.hqs.oauth.utils;

import java.util.UUID;

public class IdHelper {
    public static String generateAuthCode(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
