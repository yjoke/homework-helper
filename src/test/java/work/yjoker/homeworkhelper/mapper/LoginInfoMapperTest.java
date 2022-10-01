package work.yjoker.homeworkhelper.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import work.yjoker.homeworkhelper.HomeworkHelperApplication;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */
@SpringBootTest(classes = HomeworkHelperApplication.class)
class LoginInfoMapperTest {

    @Resource
    LoginInfoMapper loginInfoMapper;

    @Test
    void selectIDByPhone() {
        Long aLong = loginInfoMapper.selectIdByPhone("15635841133");
        System.out.println(aLong);
    }
}