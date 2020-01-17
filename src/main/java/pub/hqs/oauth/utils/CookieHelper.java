package pub.hqs.oauth.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

    public static void setCookie(HttpServletResponse response, String key, String value){
        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
    }
}
