package cn.claredai.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description 密码工具类
 * @Author ClareDai
 * @Date create in 2019/6/24 11:09
 **/
public class DefaultPasswordConfig {
    /**
     * 装配BCryptPasswordEncoder用户密码的匹配
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder()	{
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String secret = passwordEncoder.encode("app");
        System.out.println(secret);
        boolean result = passwordEncoder.matches("123456","$2a$10$l2oNqOqIHiaRm8wyzjOb1.Xd.7PEFglNyI7c7NORolnfobXoj/xJG");
        System.out.println(result);
    }
}
