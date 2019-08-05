package cn.claredai.user.controller;

import cn.claredai.common.core.annotation.LoginUser;
import cn.claredai.common.core.model.SysMenu;
import cn.claredai.common.core.model.SysRole;
import cn.claredai.common.core.model.SysUser;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.user.service.ISysMenuService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单-控制层
 *
 * @author ClareDai
 * @date 2019-06-29 09:42:33
 */
@Slf4j
@RestController
@Api(tags = "菜单相关接口")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 构建菜单树
     * @param sysMenus
     * @return
     */
    public static List<SysMenu> treeBuilder(List<SysMenu> sysMenus) {
        List<SysMenu> menus = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            if (ObjectUtils.equals(-1L, sysMenu.getParentId())) {
                recursionMenus(sysMenu, sysMenus);
                menus.add(sysMenu);
            }
        }
        return menus;
    }

    /**
     * 从一级菜单递归
     * @param sysMenu
     * @param menuList
     */
    public static void recursionMenus(SysMenu sysMenu, List<SysMenu> menuList) {
        menuList.forEach(menu -> {
            if (sysMenu.getId().equals(menu.getParentId())) {
                if (sysMenu.getSubMenus() == null) {
                    sysMenu.setSubMenus(new ArrayList<>());
                }
                recursionMenus(menu, menuList);
                sysMenu.getSubMenus().add(menu);
            }
        });
    }

    @ApiOperation(value = "根据roleId获取对应的菜单")
    @GetMapping("/menus/{roleId}/menus")
    public JsonResult findMenusByRoleId(@PathVariable Long roleId) {
        Set<Serializable> roleIds = new HashSet<>();
        roleIds.add(roleId);
        //获取该角色对应的菜单
        List<SysMenu> roleMenus = menuService.findByRoles(roleIds);
        //全部的菜单列表
        List<SysMenu> allMenus = menuService.list();
        List<Map<String, Object>> authTrees = new ArrayList<>();

        Map<Long, SysMenu> roleMenusMap = roleMenus.stream().collect(Collectors.toMap(SysMenu::getId, SysMenu -> SysMenu));

        for (SysMenu sysMenu : allMenus) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id", sysMenu.getId());
            authTree.put("name", sysMenu.getName());
            authTree.put("pId", sysMenu.getParentId());
            authTree.put("open", true);
            authTree.put("checked", false);
            if (roleMenusMap.get(sysMenu.getId()) != null) {
                authTree.put("checked", true);
            }
            authTrees.add(authTree);
        }
        return JsonResult.success(authTrees);
    }

    @ApiOperation(value = "根据roleCodes获取对应的权限")
    @SuppressWarnings("unchecked")
    @GetMapping("/menus/role/{roleCodes}")
    public JsonResult findMenuByRoles(@PathVariable String roleCodes) {
        List<SysMenu> result = null;
        if (StringUtils.isNotEmpty(roleCodes)) {
            Set<String> roleSet = (Set<String>) Convert.toCollection(HashSet.class, String.class, roleCodes);
            result = menuService.findByRoleCodes(roleSet, null);
        }
        return JsonResult.success(result);
    }

    /**
     * 给角色分配菜单
     */
    @ApiOperation(value = "角色分配菜单")
    @PostMapping("/menus/granted")
    public JsonResult setMenuToRole(@RequestBody SysMenu sysMenu) {
        boolean result = menuService.setMenuToRole(sysMenu.getRoleId(), sysMenu.getMenuIds());
        return result ? JsonResult.success() : JsonResult.fail();
    }

    /**
     * 当前登录用户的菜单
     *
     * @return
     */
    @GetMapping("/menus/current")
    @ApiOperation(value = "查询当前用户菜单")
    public JsonResult findMyMenu(@LoginUser SysUser user) {
        List<SysRole> roles = user.getRoles();
        if (CollectionUtil.isEmpty(roles)) {
            return JsonResult.success(Collections.emptyList());
        }
        List<SysMenu> menus = menuService.findByRoleCodes(roles.parallelStream().map(SysRole::getCode).collect(Collectors.toSet()), null);
        return JsonResult.success(treeBuilder(menus));
    }

//    @ApiOperation(value = "查询列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataType = "Integer"),
//            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer")
//    })
//    @GetMapping("/menus")
//    public JsonResult list(SysMenu sysMenu) {
//        PageInfo<SysMenu> pageInfo = menuService.page(sysMenu);
//        return JsonResult.success(pageInfo);
//    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/menus/{id}")
    public JsonResult findUserById(@PathVariable Serializable id) {
        SysMenu sysMenu = menuService.getById(id);
        return JsonResult.success(sysMenu);
    }

    @ApiOperation(value = "保存")
    @PostMapping("/menus")
    public JsonResult save(@RequestBody SysMenu sysMenu) {
        boolean result = menuService.save(sysMenu);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/menus")
    public JsonResult update(@RequestBody SysMenu sysMenu) {
        boolean result = menuService.updateById(sysMenu);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/menus/{id}")
    public JsonResult delete(@PathVariable Serializable id) {
        boolean result = menuService.removeById(id);
        return result ? JsonResult.success() : JsonResult.fail();
    }
}
