package pub.hqs.oauth.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthCode {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String code;
    private String userId;
    private String clientId;
    private LocalDateTime expiresIn;
    private LocalDateTime createTime;
    private Integer status;
}
