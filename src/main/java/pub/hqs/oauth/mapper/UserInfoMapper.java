package pub.hqs.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pub.hqs.oauth.entity.user.UserInfo;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
