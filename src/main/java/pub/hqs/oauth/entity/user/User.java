package pub.hqs.oauth.entity.user;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private Date createTime;
    private Date updateTime;
    private Integer status;
}
