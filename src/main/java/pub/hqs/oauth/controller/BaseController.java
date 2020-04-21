package pub.hqs.oauth.controller;

import org.apache.commons.lang3.StringUtils;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.user.UserContext;
import pub.hqs.oauth.utils.AppStatusCode;

import javax.servlet.http.HttpServletRequest;

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

    protected String getUserId(){
        return UserContext.getCurrentUser().getId();
    }
}
