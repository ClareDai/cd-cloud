package cn.claredai.common.auth.properties;

import lombok.Data;

/**
 * @Description 验证码配置
 * @Author ClareDai
 * @Date create in 2019/6/1 9:49
 **/
@Data
public class ValidateCodeProperties {
    /**
     * 设置认证通时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}
