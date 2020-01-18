package pub.hqs.oauth.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.UserDto;
import pub.hqs.oauth.dto.UserLogin;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.mapper.UserMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.service.cache.ICacheService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;

import javax.annotation.Resource;

@Service
public class UserService extends BaseService<UserMapper, User> implements IUserService {

    @Resource
    private ICacheService cacheService;

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

    public UserDto getUserInfo(String cookieName){
        if(cookieName.equals("-1")) return null;
        return cacheService.get(cookieName);
    }
}
