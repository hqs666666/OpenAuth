package pub.hqs.oauth.utils;

public class AppEnums {
    public enum GrantType {
        AuthorizationCode("authorization_code"),
        RefreshToken("refresh_token"),
        Password("password"),
        WxOpen("wxopen");

        private String value;
        GrantType(String value) {
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
}
