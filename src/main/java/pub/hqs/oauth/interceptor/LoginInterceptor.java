package pub.hqs.oauth.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pub.hqs.oauth.annotation.Anonymous;
import pub.hqs.oauth.annotation.Authorize;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.UserDto;
import pub.hqs.oauth.service.cache.ICacheService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/*
 * 验证登录
 * */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ICacheService cacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean hasAnonymous = handlerMethod.getMethod().isAnnotationPresent(Anonymous.class);
        if (hasAnonymous) return true;

        Class className = handlerMethod.getBeanType();
        boolean hasAuthorize = className.isAnnotationPresent(Authorize.class);
        if (!hasAuthorize) {
            hasAuthorize = handlerMethod.getMethod().isAnnotationPresent(Authorize.class);
            if (!hasAuthorize) return true;
        }

        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AppConstants.SESSION_NAME);
        if (user != null) return true;

        List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(p -> p.getName().equals(AppConstants.SESSION_NAME)).collect(toList());
        if (cookies != null && cookies.size() > 0) {
            String cookie = cookies.get(0).getValue();
            UserDto userDto = cacheService.get(cookie);
            setSession(request, userDto);
            if (userDto != null) return true;
        }

        responseErrorMsg(response);
        return false;
    }

    private void setSession(HttpServletRequest request, UserDto userDto) {
        HttpSession session = request.getSession();
        session.setAttribute(AppConstants.SESSION_NAME, userDto);
    }

    private void responseErrorMsg(HttpServletResponse response) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.createErrorMsg(AppStatusCode.Fail, "please login");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultMsg);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().append(json);
    }
}