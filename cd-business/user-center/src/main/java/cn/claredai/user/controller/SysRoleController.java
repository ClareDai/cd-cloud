package cn.claredai.user.controller;

import cn.claredai.common.core.model.SysRole;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.user.service.ISysRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * 系统角色-控制层
 *
 * @author ClareDai
 * @date 2019-06-29 09:42:33
 */
@Slf4j
@RestController
@Api(tags = "系统角色相关接口")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer")
    })
    @GetMapping("/roles")
    public JsonResult list(Map<String, Object> params) {
        PageInfo<SysRole> pageInfo = sysRoleService.page(params);
        return JsonResult.success(pageInfo);
    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/roles/{id}")
    public JsonResult findUserById(@PathVariable Serializable id) {
        SysRole sysRole = sysRoleService.getById(id);
        return JsonResult.success(sysRole);
    }

    @ApiOperation(value = "保存")
    @PostMapping("/roles")
    public JsonResult save(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.saveRole(sysRole);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/roles")
    public JsonResult update(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateById(sysRole);
        return result ? JsonResult.success() : JsonResult.fail();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/roles/{id}")
    public JsonResult delete(@PathVariable Long id) {
        try {
            if (id == 1L) {
                return JsonResult.fail("管理员不可以删除");
            }
            sysRoleService.deleteRole(id);
            return JsonResult.success();
        } catch (Exception e) {
            log.error("role-deleteRole-error", e);
            return JsonResult.fail();
        }
    }
}
