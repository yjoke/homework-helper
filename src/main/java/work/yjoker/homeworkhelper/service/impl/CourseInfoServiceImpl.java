package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseInfoDTO;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.mapper.CourseInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import java.util.List;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Slf4j
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo>
    implements CourseInfoService{

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Resource
    private OssWrapper ossWrapper;

    @Override
    public ApiResult<String> saveCourse(CourseInfoDTO courseInfoDTO) {

        Long createId = loginInfoMapper.selectIDByPhone(Holder.get(PHONE_HOLDER));


        CourseInfo courseInfo = courseInfoDTO.toCourseInfo(createId, ossWrapper.getUrlPrefix().length());

        boolean save = save(courseInfo);
        log.info("courseId {}", courseInfo.getId());
        return save
                ? ApiResult.success("id", courseInfo.getId().toString())
                : ApiResult.fail("创建失败");
    }

    @Override
    public ApiResult<List<CourseInfoDTO>> created() {

        Long createId = loginInfoMapper.selectIDByPhone(Holder.get(PHONE_HOLDER));

        List<CourseInfoDTO> courseInfoDTOS = courseInfoMapper.selectDTOByCreateId(createId);

        courseInfoDTOS.forEach(info -> {
            String courseImg = info.getCourseImg();
            courseImg = ossWrapper.getUrlPrefix() + courseImg;
            info.setCourseImg(courseImg);
        });

        return ApiResult.success(courseInfoDTOS);
    }

}




