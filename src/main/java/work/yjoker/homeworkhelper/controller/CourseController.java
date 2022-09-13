package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("course")
@Api(tags = "课程信息相关")
public class CourseController {
}
