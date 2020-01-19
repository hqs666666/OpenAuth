package pub.hqs.oauth.utils;

public class AppEnums {
    public enum GrantType {
        AuthorizationCode("authorization_code");

        private String value;
        GrantType(String value) {
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
}
