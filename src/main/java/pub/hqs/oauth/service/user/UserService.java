package pub.hqs.oauth.service.user;

import org.springframework.stereotype.Service;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.mapper.UserMapper;
import pub.hqs.oauth.service.BaseService;

@Service
public class UserService extends BaseService<UserMapper, User> implements IUserService {

}
