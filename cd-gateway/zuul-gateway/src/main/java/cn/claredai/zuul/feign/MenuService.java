package cn.claredai.zuul.feign;

import cn.claredai.common.core.constant.ServiceNameConstants;
import cn.claredai.common.core.model.SysMenu;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.zuul.feign.fallback.MenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/15 22:11
 **/
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
    /**
     * 角色菜单列表
     * @param roleCodes
     */
    @GetMapping(value = "/menus/role/{roleCodes}")
    JsonResult<List<SysMenu>> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);
}
