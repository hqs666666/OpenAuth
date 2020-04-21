package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pub.hqs.oauth.annotation.Anonymous;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.token.ReqAccessToken;
import pub.hqs.oauth.dto.token.ReqRefreshToken;
import pub.hqs.oauth.entity.auth.ClientUser;
import pub.hqs.oauth.service.token.ITokenService;

@Api(tags = "token相关")
@Anonymous
@RestController
@RequestMapping(value = "/oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenController extends BaseController {

    @Autowired
    private ITokenService tokenService;

    @PostMapping("access-token")
    public ResultMsg token(@Validated @RequestBody ReqAccessToken req) {
        ResultMsg resultMsg = tokenService.validClient(req);
        if (!resultMsg.getSuccess()) return resultMsg;

        resultMsg = tokenService.validCode(req.getCode(), req.getClient_id());
        if (!resultMsg.getSuccess()) return resultMsg;

        String userId = resultMsg.getData().toString();
        resultMsg = tokenService.validClientUser(userId, req.getClient_id());
        if (!resultMsg.getSuccess()) return resultMsg;
        ClientUser clientUser = (ClientUser) resultMsg.getData();

        resultMsg = tokenService.getAccessToken(userId, clientUser.getScope(), req);
        return resultMsg;
    }

    @PostMapping("refresh-token")
    public ResultMsg refreshToken(@Validated @RequestBody ReqRefreshToken req){
        ResultMsg resultMsg = tokenService.refreshToken(req);
        return resultMsg;
    }

    @GetMapping("check-token")
    public ResultMsg check(String token){
        ResultMsg resultMsg = tokenService.checkToken(token);
        return resultMsg;
    }
}
