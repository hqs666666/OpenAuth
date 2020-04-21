package pub.hqs.oauth.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.hqs.oauth.annotation.Anonymous;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.wxopen.ReqCode2Session;
import pub.hqs.oauth.service.client.IClientService;
import pub.hqs.oauth.service.wxopen.IWxOpenService;
import pub.hqs.oauth.utils.AppEnums;
import pub.hqs.oauth.utils.AppStatusCode;

@Api(tags = "微信相关")
@Anonymous
@RestController
@RequestMapping("wxopen")
public class WxOpenController extends BaseController {

    @Autowired
    private IWxOpenService wxOpenService;

    @PostMapping("/code2session")
    public ResultMsg code2session(@Validated  @RequestBody ReqCode2Session dto){
        ResultMsg resultMsg = wxOpenService.wechatLogin(dto);
        return resultMsg;
    }
}
