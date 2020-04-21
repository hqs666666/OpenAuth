package pub.hqs.oauth.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pub.hqs.oauth.annotation.Authorize;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.user.CurrentUser;
import pub.hqs.oauth.dto.user.UserContext;
import pub.hqs.oauth.service.token.ITokenService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.TokenUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class className = handlerMethod.getBeanType();
        boolean hasAuthorize = className.isAnnotationPresent(Authorize.class) || handlerMethod.getMethod().isAnnotationPresent(Authorize.class);
        if(!hasAuthorize) return true;

        String token = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(token)){
            token = token.substring(7);
            ResultMsg resultMsg = tokenService.checkToken(token);
            if(resultMsg.getSuccess()) {
                String userId = TokenUtils.getClaim(token, AppConstants.USER_ID_CLAIM);
                UserContext userContext= new UserContext(new CurrentUser(userId,"",""));
            };
            return true;
        }

        responseErrorMsg(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    private void responseErrorMsg(HttpServletResponse response) throws Exception {
        ResultMsg resultMsg = new ResultMsg().createErrorMsg(AppStatusCode.UnAuthorization, AppStatusCode.UnAuthorization.getValue());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultMsg);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().append(json);
    }
}
