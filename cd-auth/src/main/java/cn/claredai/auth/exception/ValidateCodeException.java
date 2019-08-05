package cn.claredai.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description 验证码校验异常
 * @Author ClareDai
 * @Date create in 2019/6/24 15:01
 **/
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -3859742627580263995L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
