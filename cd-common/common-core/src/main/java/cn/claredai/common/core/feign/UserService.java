package cn.claredai.common.core.feign;

import cn.claredai.common.core.constant.ServiceNameConstants;
import cn.claredai.common.core.feign.fallback.UserServiceFallbackFactory;
import cn.claredai.common.core.model.LoginAppUser;
import cn.claredai.common.core.model.SysUser;
import cn.claredai.common.core.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/2 22:03
 **/
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {
    /**
     * feign rpc访问远程/users/{username}接口
     * 查询用户实体对象SysUser
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/users/name/{username}")
    JsonResult selectByUsername(@PathVariable("username") String username);

    /**
     * feign rpc访问远程/users-anon/login接口
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/users-anon/login", params = "username")
    JsonResult<LoginAppUser> findByUsername(@RequestParam("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/users-anon/mobile", params = "mobile")
    LoginAppUser findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/users-anon/openId", params = "openId")
    LoginAppUser findByOpenId(@RequestParam("openId") String openId);
}
