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


    /**
     * 成功返回, 提示信息 msg, code = 2
     */
    public static ApiResult<String> success(String msg) {
        return ApiResult.<String>empty()
                .code(SUCCESS_NOT_DATA_CODE)
                .msg(msg);
    }

    /**
     * 标识成功, 返回包装后的字符串, 默认提示信息, code = 1
     *
     * @param key 字符串 key
     * @param data 数据
     * @return 返回字符串
     */
    public static ApiResult<String> success(String key, String data) {
        HashMap<String, String> map = new HashMap<>();
        map.put(key, data);
        ApiResult<String> result = new ApiResult<>();

        result.setCode(SUCCESS_CODE);
        result.setMsg(DEFAULT_SUCCESS);
        result.setData(map);

        return result;
    }

    /**
     * 成功返回 T 类型数据, 默认提示, code = 1
     */
    public static <T> ApiResult<T> success(T data) {
        return success(data, DEFAULT_SUCCESS);
    }

    /**
     * 成功返回 T 类型数据, 提示信息 msg, code = 1
     */
    public static <T> ApiResult<T> success(T data, String msg) {
        return new ApiResult<T>()
                .code(SUCCESS_CODE)
                .msg(msg)
                .data(data);
    }

    /**
     * 包装为 T 返回类型的失败返回, 提示信息 msg, code = 0
     */
    public static <T> ApiResult<T> fail(String msg) {
        return ApiResult.<T>empty()
                .code(FAIL_CODE)
                .msg(msg);
    }

    /**
     * 包装为 T 返回类型的错误返回, 提示信息 msg, code = 0
     */
    public static <T> ApiResult<T> error(String msg) {
        return ApiResult.<T>empty()
                .code(ERROR_CODE)
                .msg(msg);
    }

    private static <T> ApiResult<T> empty() {
        ApiResult<T> result = new ApiResult<>();
        result.setData(EMPTY);
        return result;
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
