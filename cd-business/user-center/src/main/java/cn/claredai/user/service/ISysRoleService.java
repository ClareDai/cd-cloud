package cn.claredai.user.service;


import cn.claredai.common.core.model.SysRole;
import cn.claredai.common.core.service.ISuperService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Description 系统角色-服务层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
public interface ISysRoleService extends ISuperService<SysRole> {
    boolean saveRole(SysRole sysRole);

    boolean deleteRole(Long id);

    PageInfo<SysRole> page(Map<String, Object> params);
}

