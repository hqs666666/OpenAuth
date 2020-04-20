package pub.hqs.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pub.hqs.oauth.entity.user.WxUser;

@Repository
public interface WxUserMapper extends BaseMapper<WxUser> {
}
