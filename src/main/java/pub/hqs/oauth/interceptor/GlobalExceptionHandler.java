package pub.hqs.oauth.interceptor;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.utils.AppStatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResultMsg createErrorMsg(AppStatusCode statusCode, String msg) {
        ResultMsg resultMsg = new ResultMsg();
        return resultMsg.createErrorMsg(statusCode, msg);
    }

    @ExceptionHandler({BindException.class})
    public ResultMsg handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMsgList = new ArrayList();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsgList.add(fieldError.getDefaultMessage());
        }
        return createErrorMsg(AppStatusCode.ParamsValidFail, errorMsgList.get(0));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultMsg handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return createErrorMsg(AppStatusCode.ParamsValidFail, "缺少必要的请求参数");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultMsg handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMsgList = new ArrayList();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsgList.add(fieldError.getDefaultMessage());
        }
        return createErrorMsg(AppStatusCode.ParamsValidFail, errorMsgList.get(0));
    }

    @ExceptionHandler(value = Exception.class)
    public ResultMsg handleException(Exception ex) {
        return createErrorMsg(AppStatusCode.Fail, ex.getMessage());
    }
}
