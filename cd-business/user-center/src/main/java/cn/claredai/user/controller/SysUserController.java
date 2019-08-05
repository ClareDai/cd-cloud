package cn.claredai.user.controller;

import cn.claredai.common.core.annotation.LoginUser;
import cn.claredai.common.core.model.LoginAppUser;
import cn.claredai.common.core.model.SysRole;
import cn.claredai.common.core.model.SysUser;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.user.service.ISysUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统用户-控制层
 *
 * @author ClareDai
 * @date 2019-06-29 09:42:33
 */
@Slf4j
@RestController
@Api(tags = "系统用户相关接口")
public class SysUserController {
    private static final String ADMIN_CHANGE_MSG = "超级管理员不给予修改";
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 当前登录用户 LoginAppUser
     *
     * @return
     */
    @ApiOperation(value = "根据access_token当前登录用户")
    @GetMapping("/users/current")
    public JsonResult getLoginAppUser(@LoginUser(isFull = true) SysUser user) {
        LoginAppUser loginAppUser = sysUserService.getLoginAppUser(user);
        return JsonResult.success(loginAppUser);
    }

    /**
     * 查询用户实体对象SysUser
     */
    @GetMapping(value = "/users/name/{username}")
    @ApiOperation(value = "根据用户名查询用户实体")
    public JsonResult selectByUsername(@PathVariable String username) {
        SysUser user = sysUserService.selectByUsername(username);
        return JsonResult.success(user);
    }

    /**
     * 查询用户登录对象LoginAppUser
     */
    @GetMapping(value = "/users-anon/login", params = "username")
    @ApiOperation(value = "根据用户名查询用户")
    public JsonResult findByUsername(String username) {
        LoginAppUser loginAppUser = sysUserService.findByUsername(username);
        return JsonResult.success(loginAppUser);
    }

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/users-anon/mobile", params = "mobile")
    @ApiOperation(value = "根据手机号查询用户")
    public JsonResult findByMobile(String mobile) {
        SysUser user = sysUserService.findByMobile(mobile);
        return JsonResult.success(user);
    }

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/users-anon/openId", params = "openId")
    @ApiOperation(value = "根据OpenId查询用户")
    public JsonResult findByOpenId(String openId) {
        SysUser user = sysUserService.findByOpenId(openId);
        return JsonResult.success(user);
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param id
     * @param roleIds
     */
    @PostMapping("/users/{id}/roles")
    public JsonResult setRoleToUser(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        boolean result = sysUserService.setRoleToUser(id, roleIds);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    /**
     * 获取用户的角色
     *
     * @param
     * @return
     */
    @GetMapping("/users/{id}/roles")
    public JsonResult findRolesByUserId(@PathVariable Long id) {
        List<SysRole> roles = sysUserService.findRolesByUserId(id);
        return JsonResult.success(roles);
    }

    /**
     * 修改用户状态
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "修改用户状态")
    @GetMapping("/users/updateEnabled")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "enabled", value = "是否启用", required = true, dataType = "Boolean")
    })
    public JsonResult updateEnabled(@RequestParam Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        if (checkAdmin(id)) {
            return JsonResult.fail(ADMIN_CHANGE_MSG);
        }
        return sysUserService.updateEnabled(params);
    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id
     */
    @PutMapping(value = "/users/{id}/password")
    public JsonResult resetPassword(@PathVariable Long id) {
        if (checkAdmin(id)) {
            return JsonResult.fail(ADMIN_CHANGE_MSG);
        }
        return sysUserService.updatePassword(id, null, null);
    }

    /**
     * 用户自己修改密码
     */
    @PutMapping(value = "/users/password")
    public JsonResult resetPassword(@RequestBody SysUser sysUser) {
        if (checkAdmin(sysUser.getId())) {
            return JsonResult.fail(ADMIN_CHANGE_MSG);
        }
        return sysUserService.updatePassword(sysUser.getId(), sysUser.getOldPassword(), sysUser.getNewPassword());
    }

    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer")
    })
    @GetMapping("/users")
    public JsonResult list(Map<String, Object> params) {
        PageInfo<SysUser> pageInfo = sysUserService.page(params);
        return JsonResult.success(pageInfo);
    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/users/{id}")
    public JsonResult findUserById(@PathVariable Serializable id) {
        SysUser sysUser = sysUserService.getById(id);
        return JsonResult.success(sysUser);
    }

    @ApiOperation(value = "保存")
    @PostMapping("/users")
    public JsonResult save(@RequestBody SysUser sysUser) {
        boolean result = sysUserService.save(sysUser);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/users")
    public JsonResult update(@RequestBody SysUser sysUser) {
        boolean result = sysUserService.updateById(sysUser);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/users/{id}")
    public JsonResult delete(@PathVariable Serializable id) {
        boolean result = sysUserService.delUser((Long) id);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    /**
     * 是否超级管理员
     */
    private boolean checkAdmin(long id) {
        return id == 1L;
    }
}
