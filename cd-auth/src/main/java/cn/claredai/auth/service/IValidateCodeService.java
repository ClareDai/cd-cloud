package cn.claredai.auth.service;

import cn.claredai.common.core.util.JsonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/24 15:02
 **/
public interface IValidateCodeService {
    /**
     * 保存图形验证码
     * @param deviceId 前端唯一标识
     * @param imageCode 验证码
     */
    void saveImageCode(String deviceId, String imageCode);

    JsonResult sendSmsCode(String mobile);

    /**
     * 获取验证码
     * @param deviceId 前端唯一标识/手机号
     */
    String getCode(String deviceId);

    /**
     * 删除验证码
     * @param deviceId 前端唯一标识/手机号
     */
    void remove(String deviceId);

    /**
     * 验证验证码
     */
    void validate(HttpServletRequest request);
}
