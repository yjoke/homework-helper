package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.mapper.CourseInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo>
    implements CourseInfoService{

    @Resource
    private LoginInfoService loginInfoService;

    @Resource
    private OssWrapper ossWrapper;

    @Override
    public ApiResult<String> saveCourse(CourseInfo courseInfo) {

        Long createId = loginInfoService.lambdaQuery()
                .select(LoginInfo::getId)
                .eq(LoginInfo::getPhone, Holder.get(PHONE_HOLDER))
                .one()
                .getId();

        courseInfo.setCreateId(createId);

        String courseImg = courseInfo.getCourseImg();
        courseInfo.setCourseImg(courseImg.substring(ossWrapper.getUrlPrefix().length()));

        return save(courseInfo)
                ? ApiResult.success("创建成功")
                : ApiResult.fail("创建失败");
    }
}




