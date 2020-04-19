package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.hqs.oauth.dto.ResultMsg;


@Api(tags = "用户相关")
@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController extends BaseController {

    @GetMapping("userinfo")
    public ResultMsg userInfo(@RequestHeader(value = "token",defaultValue = "1") String token){
        return createResultMsg(token);
    }


}
