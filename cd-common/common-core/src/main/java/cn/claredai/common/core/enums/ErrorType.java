package cn.claredai.common.core.enums;

import lombok.Getter;

@Getter
public enum ErrorType {

    SYSTEM_ERROR(1500, "系统异常"),

    SYSTEM_BUSY(1501, "系统繁忙,请稍候再试"),
    SYSTEM_UNAUTHORIZED(1401, "未登录"),
    SYSTEM_NO_PERMISSION(1403, "无权限"),

    GATEWAY_NOT_FOUND_SERVICE(1404, "服务未找到"),
    GATEWAY_ERROR(1400, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(1002, "网关超时"),

    ARGUMENT_NOT_VALID(1200, "请求参数校验不通过"),
    UPLOAD_FILE_SIZE_LIMIT(1201, "上传文件大小超过限制"),

    VALIDATE_CODE_ERROR(1300, "验证码校验异常");

    /**
     * 错误类型码
     */
    private int code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    ErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
