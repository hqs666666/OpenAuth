package pub.hqs.oauth.service.user;

import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.user.UserDto;
import pub.hqs.oauth.dto.user.UserLogin;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.entity.user.WxUser;
import pub.hqs.oauth.service.IBaseService;

@Service
public interface IUserService extends IBaseService<User> {
    ResultMsg validUser(UserLogin dto);
    UserDto getUser(String cookieName);
    ResultMsg addUserByWx(WxUser wxUser);
    ResultMsg getUserInfo(String userId);
}
