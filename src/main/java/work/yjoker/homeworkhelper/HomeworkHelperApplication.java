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
        // TODO 重构代码结构, 取消所有的 service 层互相调用, 使用 mapper 实现需要调用的 service 层逻辑
        log.info("代码 service 层存在循环引用, 默认不推荐这么使用, 现在是强制使用了, 记得修改");
    }

}
