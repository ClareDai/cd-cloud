package cn.claredai.common.core.util;

import cn.claredai.common.core.enums.ErrorType;
import cn.claredai.common.core.exception.BaseException;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/1 0:05
 **/
@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Getter
@Setter
public class JsonResult<T> {
    public static final int SUCCESSFUL_CODE = 200;
    public static final String SUCCESSFUL_MSG = "请求成功";

    @ApiModelProperty(value = "请求结果code", required = true)
    private int code;
    @ApiModelProperty(value = "请求结果描述信息")
    private String msg;
    @ApiModelProperty(value = "请求结果生成时间戳")
    private Instant timestamp;
    @ApiModelProperty(value = "处理结果数据信息")
    private T data;

    public JsonResult() {
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    /**
     * @param errorType
     */
    public JsonResult(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMsg();
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    /**
     * @param errorType
     * @param data
     */
    public JsonResult(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static JsonResult success(Object data) {
        return new JsonResult(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static JsonResult success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static JsonResult fail() {
        return new JsonResult(ErrorType.SYSTEM_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @param baseException
     * @return Result
     */
    public static JsonResult fail(BaseException baseException) {
        return fail(baseException, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static JsonResult fail(BaseException baseException, Object data) {
        return new JsonResult<>(baseException.getErrorType(), data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @param data
     * @return Result
     */
    public static JsonResult fail(ErrorType errorType, Object data) {
        return new JsonResult<>(errorType, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @return Result
     */
    public static JsonResult fail(ErrorType errorType) {
        return JsonResult.fail(errorType, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static JsonResult fail(Object data) {
        return new JsonResult<>(ErrorType.SYSTEM_ERROR, data);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static JsonResult fail(String msg) {
        return new JsonResult(ErrorType.SYSTEM_ERROR.getCode(), msg, null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static JsonResult fail(int code, String msg) {
        return new JsonResult(code, msg, null);
    }

    /**
     * 成功code=200
     *
     * @return true/false
     */
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return SUCCESSFUL_CODE == this.code;
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JSONField(serialize = false)
    public boolean isFail() {
        return !isSuccess();
    }
}
