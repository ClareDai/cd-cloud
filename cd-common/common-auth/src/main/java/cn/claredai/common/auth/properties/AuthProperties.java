package cn.claredai.common.auth.properties;

import lombok.Data;

/**
 * @Description 认证配置
 * @Author ClareDai
 * @Date create in 2019/6/1 9:41
 **/
@Data
public class AuthProperties {
    /**
     * 要认证的url
     */
    private String[] httpUrls = {};

    /**
     * 是否开启url权限验证
     */
    private boolean urlEnabled = false;
}
