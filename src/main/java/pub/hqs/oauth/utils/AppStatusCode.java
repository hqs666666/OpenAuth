package pub.hqs.oauth.utils;

public enum AppStatusCode {
    Ok("成功", 0),
    Fail("系统异常", 1),
    ClientNotFount("客户端不存在",10001),
    ClientDisabled("客户端已被禁用",10002),
    UnsupportedResponseType("不支持的响应类型",10003),
    UnsupportedScope("不支持的scope",10004),
    UserValidFail("用户验证失败",20001);

    private int code;
    private String value;

    private AppStatusCode(String value, int code) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getValue(){
        return this.value;
    }
}
