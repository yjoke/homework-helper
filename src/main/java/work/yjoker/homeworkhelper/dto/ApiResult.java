package work.yjoker.homeworkhelper.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import static work.yjoker.homeworkhelper.constant.ApiResultConstants.*;

/**
 * @author HeYunjia
 */
@Getter
@ApiModel("统一返回类")
public class ApiResult <T> {

    @ApiModelProperty("编码: 2 成功但没有数据, 1 成功, 0 失败, -1 异常")
    private int code;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    private ApiResult() {}

    public static ApiResult<String> success() {
        return ApiResult.success(EMPTY)
                .code(SUCCESS_NOT_DATA_CODE);
    }

    public static <T> ApiResult<T> success(T object) {
        return new ApiResult<T>()
                .code(SUCCESS_CODE)
                .msg(DEFAULT_SUCCESS)
                .data(object);
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

    public ApiResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    private ApiResult<T> code(Integer code) {
        this.code = code;
        return this;
    }
}
