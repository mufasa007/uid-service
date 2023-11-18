package site.activeclub.uid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.activeclub.uid.common.utils.SnowflakeUuidUtil;

/**
 * @author wanyu
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        SnowflakeUuidUtil.initStartId();
    }

}
