package pub.hqs.oauth.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;

public class HttpHelper {
    public static HashMap<String,Object> get(String url){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            final Call call = client.newCall(request);
            Response response = call.execute();
            String json = response.body().string();
            HashMap<String,Object> map = JsonHelper.stringToMap(json);
            return map;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException("请求失败");
        }
    }
}
