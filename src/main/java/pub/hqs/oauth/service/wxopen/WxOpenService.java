package pub.hqs.oauth.service.wxopen;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.user.UserDto;
import pub.hqs.oauth.dto.user.UserLogin;
import pub.hqs.oauth.dto.wxopen.ReqCode2Session;
import pub.hqs.oauth.dto.wxopen.ReqLogin;
import pub.hqs.oauth.dto.wxopen.SessionBag;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.entity.user.UserInfo;
import pub.hqs.oauth.entity.user.WxUser;
import pub.hqs.oauth.mapper.WxUserMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.service.cache.ICacheService;
import pub.hqs.oauth.service.client.IClientService;
import pub.hqs.oauth.service.token.ITokenService;
import pub.hqs.oauth.service.user.IUserService;
import pub.hqs.oauth.utils.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class WxOpenService extends BaseService<WxUserMapper, WxUser> implements IWxOpenService {
    @Resource
    private IUserService userService;
    @Resource
    private AppSetting appSetting;
    @Resource
    private ICacheService cacheService;
    @Resource
    private ITokenService tokenService;
    @Resource
    private IClientService clientService;

    @Transactional
    public ResultMsg wechatLogin(ReqCode2Session dto){
        ResultMsg resultMsg = clientService.getClient(dto.getClient_id(),dto.getClient_secret());
        if(!resultMsg.getSuccess()) return resultMsg;

        if(!dto.getGrant_type().equals(AppEnums.GrantType.WxOpen.getValue()))
            return createErrorMsg(AppStatusCode.GrantTypeFail);

        if(!resultMsg.getSuccess()) return resultMsg;
        Client client = (Client)resultMsg.getData();

        resultMsg = getWxInfoByCode(dto.getCode());
        if(!resultMsg.getSuccess()) return resultMsg;

        SessionBag bag = (SessionBag)resultMsg.getData();
        WxUser wxUser = getUserByWx(bag.getOpenId());
        if(wxUser != null){
            resultMsg = existUser(wxUser.getUserId(), client);
        }else{
            WxUser user = setWxUser(dto, bag.getOpenId());
            resultMsg = notExistUser(user, client);
        }
        return resultMsg;
    }

    public ResultMsg login(ReqLogin dto){
        ResultMsg resultMsg = clientService.getClient(dto.getClient_id(),dto.getClient_secret());
        if(!resultMsg.getSuccess()) return resultMsg;

        if(!dto.getGrant_type().equals(AppEnums.GrantType.Password.getValue()))
            return createErrorMsg(AppStatusCode.GrantTypeFail);

        if(!resultMsg.getSuccess()) return resultMsg;
        Client client = (Client)resultMsg.getData();

        UserLogin loginDto = new UserLogin(dto.getUsername(),dto.getPassword());
        resultMsg = userService.validUser(loginDto);
        if(!resultMsg.getSuccess()) return resultMsg;

        String userId = resultMsg.getData().toString();
        resultMsg = userService.getUserInfo(userId);
        if(!resultMsg.getSuccess()) return resultMsg;
        UserInfo userInfo = (UserInfo)resultMsg.getData();

        resultMsg = tokenService.getTokenBag(userInfo.getId(), userInfo.getNickname(), client,AppEnums.GrantType.WxOpen.getValue(),"userinfo");
        return resultMsg;
    }

    private WxUser setWxUser(ReqCode2Session dto, String openId){
        WxUser user = new WxUser();
        user.setOpenId(openId);
        user.setNickName(dto.getNickName());
        user.setAvatarUrl(dto.getAvatarUrl());
        user.setCity(dto.getCity());
        user.setCountry(dto.getCountry());
        user.setProvince(dto.getProvince());
        user.setGender(dto.getGender());
        user.setUnionId(dto.getUnionId());
        return user;
    }

    private ResultMsg existUser(String userId, Client client){
        ResultMsg resultMsg = userService.getUserInfo(userId);
        if(!resultMsg.getSuccess()) return resultMsg;
        UserInfo user = (UserInfo) resultMsg.getData();
        resultMsg = tokenService.getTokenBag(user.getId(),user.getNickname(),client,AppEnums.GrantType.WxOpen.getValue(),"userinfo");
        return resultMsg;
    }

    private ResultMsg notExistUser(WxUser info, Client client){
        if(info == null) return createErrorMsg(AppStatusCode.UserValidFail);
        ResultMsg resultMsg = userService.addUserByWx(info);
        if(!resultMsg.getSuccess()) return createErrorMsg(AppStatusCode.AddWeChatUserFail);
        UserInfo user = (UserInfo) resultMsg.getData();

        info.setId(IdHelper.generateIdentity());
        info.setCreateTime(LocalDateTime.now());
        info.setUserId(user.getId());
        Boolean result = save(info);
        if(!result) return createErrorMsg(AppStatusCode.AddWeChatUserFail);

        resultMsg = tokenService.getTokenBag(user.getId(),user.getNickname(),client,AppEnums.GrantType.WxOpen.getValue(),"userinfo");
        return resultMsg;
    }

    private WxUser getUserByWx(String openId){
        QueryWrapper query = new QueryWrapper<WxUser>().eq("open_id",openId);
        WxUser user = getOne(query);
        return user;
    }

    private ResultMsg getWxInfoByCode(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+ appSetting.appId+"&secret="+appSetting.secret+"&js_code="+code+"&grant_type=authorization_code";
        HashMap result = HttpHelper.get(url);
        if(!result.containsKey("openid")){
            return createErrorMsg(AppStatusCode.WeChatCodeFail);
        }

        SessionBag bag = new SessionBag();
        bag.setOpenId(result.get("openid").toString());
        bag.setSessionKey(result.get("session_key").toString());
        if(result.containsKey("unionid")){
            bag.setUnionId(result.get("unionid").toString());
        }
        //cacheService.set("openId-"+bag.getOpenId(),bag,60*60*2);

        return createResultMsg(bag);
    }
}
