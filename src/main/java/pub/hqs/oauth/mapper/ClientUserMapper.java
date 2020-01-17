package pub.hqs.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pub.hqs.oauth.entity.auth.ClientUser;

@Repository
public interface ClientUserMapper extends BaseMapper<ClientUser> {
}
