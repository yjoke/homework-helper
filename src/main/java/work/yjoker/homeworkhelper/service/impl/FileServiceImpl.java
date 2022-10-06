package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.service.FileService;
import work.yjoker.homeworkhelper.util.MemoryUnit;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

import static work.yjoker.homeworkhelper.constant.FileConstants.*;

/**
 * @author HeYunjia
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private OssWrapper ossWrapper;

    @Override
    public ApiResult<String> saveAvatar(MultipartFile file) {
        return saveFileWrapper(file, MAX_AVATAR_SIZE, AVATAR_PATH_KEY);
    }

    @Override
    public ApiResult<String> saveCover(MultipartFile file) {
        return saveFileWrapper(file, MAX_COVER_SIZE, COVER_PATH_KEY);
    }

    @Override
    public ApiResult<String> saveCourseResource(MultipartFile file) {
        return saveFileWrapper(file, MAX_RESOURCE_SIZE, RESOURCE_PATH_KEY);
    }

    @Override
    public ApiResult<String> saveHomework(MultipartFile file) {
        return saveFileWrapper(file, MAX_HOMEWORK_SIZE, HOMEWORK_PATH_KEY);
    }


    /**
     * 整合保存操作
     *
     * @param file 文件
     * @param size 文件最大大小 Byte
     * @param key 返回单位
     * @return 返回前端数据
     */
    private ApiResult<String> saveFileWrapper(MultipartFile file, long size, String key) {
        String filePath = saveFile(file, size);

        if (StrUtil.isEmpty((filePath))) return ApiResult.fail("上传失败");

        String resourceUrl = ossWrapper.getUrlPrefix() + filePath;

        return ApiResult.success(key, resourceUrl);
    }

    /**
     * 保存不超过 maxSize 字节的文件 file
     */
    private String saveFile(MultipartFile file, long maxSize) {
        return ossWrapper.saveFile(
                getFileInputStream(file, maxSize),
                file.getSize(),
                MemoryUnit.BYTE,
                getFileSuffix(file)
        );
    }

    /**
     * 校验文件大小, 并获取上传文件的输入流
     * maxSize 单位 Byte
     */
    private InputStream getFileInputStream(MultipartFile file, long maxSize) {
        if (file.getSize() > maxSize) return null;

        try {
            return file.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取上传文件的后缀
     */
    private String getFileSuffix(MultipartFile file) {
        String filename = file.getOriginalFilename();

        if (StrUtil.isEmpty(filename)) return "";

        return filename.substring(filename.lastIndexOf("."));
    }
}
