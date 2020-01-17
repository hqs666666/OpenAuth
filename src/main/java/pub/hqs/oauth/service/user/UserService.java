package pub.hqs.oauth.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.UserLogin;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.mapper.UserMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.service.cache.ICacheService;
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

        cacheService.set(user.getId(), user);
        return createResultMsg(user.getId());
    }
}
