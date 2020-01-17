package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pub.hqs.oauth.dto.AuthorizationInfo;
import pub.hqs.oauth.dto.ClientInfo;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.UserLogin;
import pub.hqs.oauth.service.authorization.IAuthorizationService;
import pub.hqs.oauth.service.user.IUserService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.CookieHelper;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Api(tags = "授权相关接口")
@Controller
@RequestMapping(value = "/oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
public class OAuthController extends BaseController {

    @Autowired
    private IAuthorizationService authorizationService;
    @Autowired
    private IUserService userService;

    @GetMapping("authorize")
    public ModelAndView authorize(@Validated AuthorizationInfo dto) {
        ModelAndView modelAndView = new ModelAndView("login");
        ResultMsg resultMsg = authorizationService.getClient(dto);
        modelAndView.addObject("resultMsg", resultMsg);
        modelAndView.addObject("clientInfo", (ClientInfo) resultMsg.getData());
        return modelAndView;
    }

    @PostMapping("authorize")
    public ModelAndView login(@Validated UserLogin dto, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("authorize");
        ResultMsg resultMsg = userService.validUser(dto);
        if (resultMsg.getSuccess()) {
            CookieHelper.setCookie(response, AppConstants.SESSION_NAME, resultMsg.getData().toString());
            resultMsg = authorizationService.getClient(dto);
        }

        modelAndView.addObject("resultMsg", resultMsg);
        modelAndView.addObject("clientInfo", (ClientInfo) resultMsg.getData());
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("agree")
    public ResultMsg agree(@RequestBody @Validated AuthorizationInfo dto) {
        ResultMsg result = authorizationService.getAuthorizationCode(dto);
        if (!result.getSuccess()) return result;

        String redirectUrl = URLDecoder.decode(dto.getRedirect_uri());
        String joinStr = redirectUrl.contains("?") ? "&" : "?";
        redirectUrl += joinStr + "code=" + result.getData() + "&state=" + dto.getState();
        result.setData(redirectUrl);
        return result;
    }

    @PostMapping("access_token")
    public ResultMsg token() {
        return null;
    }
}
