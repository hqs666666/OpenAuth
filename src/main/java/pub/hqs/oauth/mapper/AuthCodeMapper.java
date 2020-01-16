package pub.hqs.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pub.hqs.oauth.entity.auth.AuthCode;

@Repository
public interface AuthCodeMapper extends BaseMapper<AuthCode> {
}
