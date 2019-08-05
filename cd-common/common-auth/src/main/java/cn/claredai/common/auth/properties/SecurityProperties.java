package cn.claredai.common.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/1 9:51
 **/
@Setter
@Getter
@ConfigurationProperties(prefix = "cd.security")
@RefreshScope
public class SecurityProperties {
    private AuthProperties auth = new AuthProperties();

    private PermitProperties ignore = new PermitProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();
}
