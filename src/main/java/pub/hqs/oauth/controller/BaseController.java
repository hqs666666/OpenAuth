package pub.hqs.oauth.controller;

import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.utils.AppStatusCode;

public class BaseController {
    /*
    * 返回正确的数据结构
    * */
    protected ResultMsg createResultMsg(Object data) {
        ResultMsg result = new ResultMsg();
        return result.createResultMsg(data);
    }

    protected ResultMsg createErrorMsg(AppStatusCode statusCode){
        ResultMsg result = new ResultMsg();
        return result.createErrorMsg(statusCode);
    }
}
