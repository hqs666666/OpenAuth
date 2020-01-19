package pub.hqs.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pub.hqs.oauth.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor customInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(customInterceptor());
    }
}
