package pub.hqs.oauth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ResultMsg {
    private Boolean Success;
    private Integer Code;
    private String Message;
    private Object Data;

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(this.Data);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
