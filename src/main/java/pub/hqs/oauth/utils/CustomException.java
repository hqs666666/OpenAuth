package pub.hqs.oauth.utils;

public class CustomException extends RuntimeException {
    public CustomException(){
        super();
    }

    public CustomException(String message){
        super(message);
    }
}
