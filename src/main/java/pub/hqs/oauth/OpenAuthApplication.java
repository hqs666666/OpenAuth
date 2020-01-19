package pub.hqs.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("pub.hqs.oauth.mapper")
public class OpenAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAuthApplication.class, args);
    }

}
