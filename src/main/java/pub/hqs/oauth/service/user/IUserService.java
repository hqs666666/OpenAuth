package pub.hqs.oauth.service.user;

import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.UserDto;
import pub.hqs.oauth.dto.UserLogin;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.service.IBaseService;

@Service
public interface IUserService extends IBaseService<User> {
    ResultMsg validUser(UserLogin dto);
    UserDto getUserInfo(String cookieName);
}
