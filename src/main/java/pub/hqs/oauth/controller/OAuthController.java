package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pub.hqs.oauth.dto.AuthorizationCode;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.service.authorization.IAuthorizationService;

import java.net.URLDecoder;

@Api(tags = "授权相关接口")
@RestController
@RequestMapping(value = "/oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
public class OAuthController extends BaseController {

    @Autowired
    private IAuthorizationService authorizationService;

    @GetMapping("authorize")
    @ApiOperation(value = "获取授权码", notes = "这里填写接口方法描述")
    public ModelAndView authorize(@Validated AuthorizationCode dto) {
        ResultMsg result = authorizationService.getAuthorizationCode(dto);
        if (!result.getSuccess()) return null;

        String redirectUrl = URLDecoder.decode(dto.getRedirect_uri());
        String joinStr = redirectUrl.contains("?") ? "&" : "?";
        redirectUrl += joinStr + "code" + result.getData() + "&state=" + dto.getState();
        result.setData(redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @PostMapping("access_token")
    public ResultMsg token() {
        return null;
    }
}
