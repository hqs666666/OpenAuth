package pub.hqs.oauth.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ClientUser {
    @TableId(type = IdType.UUID)
    private String id;
    private String clientId;
    private String userId;
    private String scope;
}
