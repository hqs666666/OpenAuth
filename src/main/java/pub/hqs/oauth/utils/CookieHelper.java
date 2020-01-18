package pub.hqs.oauth.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
    public static void setCookie(HttpServletResponse response, String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(AppConstants.SESSION_TIME);
        response.addCookie(cookie);
    }
}
