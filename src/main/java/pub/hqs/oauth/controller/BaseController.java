package pub.hqs.oauth.controller;

import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.utils.AppStatusCode;

public class BaseController {
    /*
    * 返回正确的数据结构
    * */
    protected ResultMsg CreateResultMsg(Object data) {
        AppStatusCode statusCode = AppStatusCode.Ok;
        ResultMsg result = new ResultMsg();
        result.setSuccess(true);
        result.setMessage(statusCode.getValue());
        result.setCode(statusCode.getCode());
        result.setData(data);
        return result;
    }
}
