package pub.hqs.oauth.utils;

import java.util.Random;

public class AccountHelper {
    public static String generateLoginName(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 7; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String generatePassword(){
        return "123456";
    }
}
