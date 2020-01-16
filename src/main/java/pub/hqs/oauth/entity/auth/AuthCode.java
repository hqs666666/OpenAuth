package pub.hqs.oauth.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class AuthCode {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String code;
    private String clientId;
    private Date createTime;
    private Integer status;
}
