package cn.claredai.zuul.service.impl;

import cn.claredai.common.auth.service.impl.DefaultPermissionServiceImpl;
import cn.claredai.common.core.model.SysMenu;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.zuul.feign.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 请求权限判断service
 * @Author ClareDai
 * @Date create in 2019/7/15 22:37
 **/
@Slf4j
@Component("permissionService")
public class PermissionServiceImpl extends DefaultPermissionServiceImpl {
    @Resource
    private MenuService menuService;

    @Override
    public List<SysMenu> findMenuByRoleCodes(String roleCodes) {
        JsonResult<List<SysMenu>> result = menuService.findByRoleCodes(roleCodes);
        return result.getData();
    }
}
