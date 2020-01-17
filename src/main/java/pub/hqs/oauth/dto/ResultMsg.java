package pub.hqs.oauth.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class ResultMsg {
    private Boolean Success;
    private Integer Code;
    private String Message;
    private Object Data;

    public String toJson(){
        return JSON.toJSONString(this.Data);
    }
}
