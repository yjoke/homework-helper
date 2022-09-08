package work.yjoker.homeworkhelper;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableKnife4j
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("work.yjoker.homeworkhelper.mapper")
public class HomeworkHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkHelperApplication.class, args);
        log.info("******** 项目启动成功 ********");
    }

}
