package cn.claredai.auth.service.impl;

import cn.claredai.auth.service.CDUserDetailsService;
import cn.claredai.common.core.feign.UserService;
import cn.claredai.common.core.model.LoginAppUser;
import cn.claredai.common.core.util.JsonResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/24 12:13
 **/
@Slf4j
@Service
public class UserDetailServiceImpl implements CDUserDetailsService, SocialUserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        JsonResult<LoginAppUser> result = userService.findByUsername(username);
        LoginAppUser loginAppUser = result.getData();
        if (loginAppUser == null) {
            throw new InternalAuthenticationServiceException("用户名或密码错误");
        }
        return checkUser(loginAppUser);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String openId) {
        LoginAppUser loginAppUser = userService.findByOpenId(openId);
        return checkUser(loginAppUser);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        LoginAppUser loginAppUser = userService.findByMobile(mobile);
        return checkUser(loginAppUser);
    }

    private LoginAppUser checkUser(LoginAppUser loginAppUser) {
        if (loginAppUser != null && !loginAppUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        return loginAppUser;
    }
}
