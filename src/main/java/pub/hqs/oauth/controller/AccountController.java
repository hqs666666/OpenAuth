package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.hqs.oauth.annotation.Authorize;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.service.user.IUserService;

import javax.annotation.Resource;

@Api(tags = "用户相关")
@Authorize
@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController extends BaseController {

    @Resource
    private IUserService userService;

    @GetMapping("userinfo")
    public ResultMsg userInfo(){
        ResultMsg resultMsg = userService.getUserInfo(getUserId());
        return resultMsg;
    }
}
