package cn.claredai.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/24 12:12
 **/
public interface CDUserDetailsService extends UserDetailsService {
    /**
     * 根据电话号码查询用户
     *
     * @param mobile
     * @return
     */
    UserDetails loadUserByMobile(String mobile);
}
