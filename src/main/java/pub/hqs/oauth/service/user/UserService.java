package pub.hqs.oauth.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.user.UserDto;
import pub.hqs.oauth.dto.user.UserLogin;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.entity.user.UserInfo;
import pub.hqs.oauth.entity.user.WxUser;
import pub.hqs.oauth.mapper.UserInfoMapper;
import pub.hqs.oauth.mapper.UserMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.service.cache.ICacheService;
import pub.hqs.oauth.utils.AccountHelper;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.IdHelper;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class UserService extends BaseService<UserMapper, User> implements IUserService {

    @Resource
    private ICacheService cacheService;
    @Resource
    private UserInfoMapper userInfoMapper;

    public ResultMsg validUser(UserLogin dto) {
        User user = getOne(new QueryWrapper<User>()
                .eq("username", dto.getUsername())
                .eq("password", dto.getPassword()));
        if (user == null) return createErrorMsg(AppStatusCode.UserValidFail);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUsername());
        userDto.setMobilePhone(user.getMobile());
        cacheService.set(user.getId(), userDto, AppConstants.SESSION_TIME);
        return createResultMsg(user.getId());
    }

    public UserDto getUser(String cookieName){
        if(cookieName.equals("-1")) return null;
        return cacheService.get(cookieName);
    }

    public ResultMsg getUserInfo(String userId){
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if(userInfo == null) return createErrorMsg(AppStatusCode.UserValidFail);
        return createResultMsg(userInfo);
    }

    public ResultMsg addUserByWx(WxUser wxUser){
        User user = new User();
        user.setId(IdHelper.generateIdentity());
        user.setUsername(AccountHelper.generateLoginName());
        user.setPassword(AccountHelper.generatePassword());
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(1);
        Boolean result = save(user);
        if(!result) return createErrorMsg(AppStatusCode.AddWeChatUserFail);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(wxUser.getNickName());
        userInfo.setGender(wxUser.getGender());
        userInfo.setAvatarUrl(wxUser.getAvatarUrl());
        userInfo.setCountry(wxUser.getCountry());
        userInfo.setProvince(wxUser.getProvince());
        userInfo.setCity(wxUser.getCity());
        user.setCreateTime(LocalDateTime.now());
        result = userInfoMapper.insert(userInfo) > 0;
        if(!result) return createErrorMsg(AppStatusCode.AddWeChatUserFail);
        return createResultMsg(userInfo);
    }
}
