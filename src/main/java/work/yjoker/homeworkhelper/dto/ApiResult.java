package work.yjoker.homeworkhelper.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;

import static work.yjoker.homeworkhelper.constant.ApiResultConstants.*;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("统一返回类")
public class ApiResult <T> {

    @ApiModelProperty("编码: 2 成功但没有数据, 1 成功, 0 失败, -1 异常")
    private Integer code;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("数据")
    private Object data;

    private ApiResult() {}


    public static ApiResult<String> success(String msg) {
        return success().msg(msg);
    }

    public static ApiResult<String> success() {
        ApiResult<String> result = new ApiResult<>();

        result.setCode(SUCCESS_NOT_DATA_CODE);
        result.setMsg(DEFAULT_SUCCESS);
        result.setData(EMPTY);

        return result;
    }

    public static ApiResult<String> success(String key, String data) {
        HashMap<String, String> map = new HashMap<>();
        map.put(key, data);
        ApiResult<String> result = new ApiResult<>();

        result.setCode(SUCCESS_CODE);
        result.setMsg(DEFAULT_SUCCESS);
        result.setData(map);

        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        return success(data, DEFAULT_SUCCESS);
    }

    public static <T> ApiResult<T> success(T data, String msg) {
        return new ApiResult<T>()
                .code(SUCCESS_CODE)
                .msg(msg)
                .data(data);
    }

    public static ApiResult<String> fail() {
        return ApiResult.fail(DEFAULT_FAIL);
    }

    public static ApiResult<String> fail(String msg) {
        return new ApiResult<String>()
                .code(FAIL_CODE)
                .msg(msg)
                .data(EMPTY);
    }

    public static ApiResult<String> error() {
        return ApiResult.error(DEFAULT_ERROR);
    }

    public static ApiResult<String> error(String msg) {
        return new ApiResult<String>()
                .code(ERROR_CODE)
                .msg(msg)
                .data(EMPTY);
    }

    private ApiResult<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    private ApiResult<T> msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    private ApiResult<T> data(T data) {
        this.setData(data);
        return this;
    }
}
