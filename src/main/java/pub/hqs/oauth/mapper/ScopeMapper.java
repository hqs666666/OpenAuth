package pub.hqs.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pub.hqs.oauth.entity.auth.Scope;

@Repository
public interface ScopeMapper extends BaseMapper<Scope> {
}
