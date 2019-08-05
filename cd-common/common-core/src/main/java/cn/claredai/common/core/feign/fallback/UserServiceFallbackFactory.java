package cn.claredai.common.core.feign.fallback;

import cn.claredai.common.core.feign.UserService;
import cn.claredai.common.core.model.LoginAppUser;
import cn.claredai.common.core.model.SysUser;
import cn.claredai.common.core.util.JsonResult;
import com.alibaba.fastjson.JSON;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/3 0:01
 **/
@Slf4j
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public JsonResult selectByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return JsonResult.success(new SysUser());
            }

            @Override
            public JsonResult findByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return JsonResult.success(new SysUser());
            }

            @Override
            public LoginAppUser findByMobile(String mobile) {
                log.error("通过手机号查询用户异常:{}", mobile, throwable);
                return new LoginAppUser();
            }

            @Override
            public LoginAppUser findByOpenId(String openId) {
                log.error("通过openId查询用户异常:{}", openId, throwable);
                return new LoginAppUser();
            }
        };
    }
}
