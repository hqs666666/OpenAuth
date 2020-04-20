package pub.hqs.oauth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class JsonHelper {
    public static String objectToString(Object obj){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(obj);
            return jsonValue;
        }catch (Exception e){
            throw new CustomException("序列化失败");
        }
    }

    public static HashMap<String,Object> stringToMap(String str){
        try{
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String,Object> map = mapper.readValue(str, HashMap.class);
            return map;
        }catch (Exception e){
            throw new CustomException("序列化失败");
        }
    }
}
