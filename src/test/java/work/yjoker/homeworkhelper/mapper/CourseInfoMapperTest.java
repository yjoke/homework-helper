package work.yjoker.homeworkhelper.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import work.yjoker.homeworkhelper.HomeworkHelperApplication;
import work.yjoker.homeworkhelper.dto.CourseInfoDTO;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author HeYunjia
 */
@SpringBootTest(classes = HomeworkHelperApplication.class)
class CourseInfoMapperTest {

    @Resource
    CourseInfoMapper courseInfoMapper;

    @Test
    void selectDTOByCreateId() {
        List<CourseInfoDTO> courseInfoDTOS = courseInfoMapper.selectCreatedDTOByCreateId(1568407709848563714L);
        System.out.println(courseInfoDTOS);
    }
}