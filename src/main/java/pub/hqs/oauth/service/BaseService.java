package pub.hqs.oauth.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.utils.AppStatusCode;

public class BaseService<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements IBaseService<T> {

    protected ResultMsg createResultMsg(Object data){
        ResultMsg result = new ResultMsg();
        result.setSuccess(true);
        result.setMessage(AppStatusCode.Ok.getValue());
        result.setCode(AppStatusCode.Ok.getCode());
        result.setData(data);
        return result;
    }

    protected ResultMsg createErrorMsg(AppStatusCode statusCode){
        ResultMsg result = new ResultMsg();
        result.setSuccess(false);
        result.setMessage(statusCode.getValue());
        result.setCode(statusCode.getCode());
        return result;
    }
}
