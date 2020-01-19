package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pub.hqs.oauth.annotation.Authorize;
import pub.hqs.oauth.dto.AuthorizationInfo;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.UserDto;
import pub.hqs.oauth.dto.UserLogin;
import pub.hqs.oauth.service.authorization.IAuthorizationService;
import pub.hqs.oauth.service.user.IUserService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.CookieHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(tags = "授权相关接口")
@Controller
@RequestMapping(value = "/oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
public class OAuthController extends BaseController {

    @Autowired
    private IAuthorizationService authorizationService;
    @Autowired
    private IUserService userService;

    @GetMapping("authorize")
    public String authorize(@Validated AuthorizationInfo dto, @CookieValue(value = AppConstants.SESSION_NAME,defaultValue = "-1") String cookie, Model model) {
        ResultMsg resultMsg = authorizationService.validClient(dto);
        if (resultMsg.getSuccess()) {
            UserDto user = userService.getUserInfo(cookie);
            if (user != null) {
                Boolean hasAuth = authorizationService.hasAuthorizeClient(user.getId(), dto.getClient_id());
                if (hasAuth) {
                    resultMsg = authorizationService.getRedirectUrl(dto, user.getId());
                    if (resultMsg.getSuccess()) {
                        return "redirect:" + resultMsg.getData().toString();
                    }
                }
            }
            resultMsg = authorizationService.getClient(dto);
            model.addAttribute("hasLogin", user != null);
        }
        model.addAttribute("resultMsg", resultMsg);
        return "authorize";
    }

    @ResponseBody
    @PostMapping("authorize")
    public ResultMsg login(@RequestBody UserLogin dto, HttpServletResponse response) {
        ResultMsg resultMsg = userService.validUser(dto);
        if (resultMsg.getSuccess()) {
            CookieHelper.setCookie(response, AppConstants.SESSION_NAME, resultMsg.getData().toString());
            //如果已经授权过了，登录后直接跳转回页面
        }
        return resultMsg;
    }

    @Authorize
    @ResponseBody
    @PostMapping("agree")
    public ResultMsg agree(@Validated @RequestBody AuthorizationInfo dto, HttpServletRequest request) {
        ResultMsg resultMsg = authorizationService.validClient(dto);
        if(!resultMsg.getSuccess()) return resultMsg;
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AppConstants.SESSION_NAME);
        if (user == null) return createErrorMsg(AppStatusCode.UserValidFail);
        resultMsg = authorizationService.getRedirectUrl(dto, user.getId());
        return resultMsg;
    }
}
