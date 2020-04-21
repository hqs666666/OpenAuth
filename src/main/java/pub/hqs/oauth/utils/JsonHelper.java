package pub.hqs.oauth.utils;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;

public class JsonHelper {
    public static String objectToString(Object obj){
        try{
            String jsonValue = JSON.toJSONString(obj);
            return jsonValue;
        }catch (Exception e){
            throw new CustomException("序列化失败");
        }
    }

    public static HashMap<String,Object> stringToMap(String str){
        try{
            HashMap<String,Object> map = JSON.parseObject(str,HashMap.class);
            return map;
        }catch (Exception e){
            throw new CustomException("序列化失败");
        }
    }
}
