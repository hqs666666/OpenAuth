package pub.hqs.oauth.service.wxopen;

import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.wxopen.ReqCode2Session;
import pub.hqs.oauth.entity.user.WxUser;
import pub.hqs.oauth.service.IBaseService;

public interface IWxOpenService extends IBaseService<WxUser> {
    ResultMsg wechatLogin(ReqCode2Session dto);
}
