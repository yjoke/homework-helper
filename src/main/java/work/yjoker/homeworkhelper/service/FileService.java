package work.yjoker.homeworkhelper.service;

import org.springframework.web.multipart.MultipartFile;
import work.yjoker.homeworkhelper.dto.ApiResult;

/**
 * @author HeYunjia
 */
public interface FileService {
    ApiResult<String> saveAvatar(MultipartFile file);

    ApiResult<String> saveCover(MultipartFile file);
}
