package cn.claredai.common.auth.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 请求权限判断service
 * @Author ClareDai
 * @Date create in 2019/6/19 15:57
 **/
public interface IPermissionService {
    /**
     * 判断请求是否有权限
     *
     * @param authentication 认证信息
     * @return 是否有权限
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
