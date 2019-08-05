package cn.claredai.user.service.impl;

import cn.claredai.common.core.model.SysMenu;
import cn.claredai.common.core.service.impl.SuperServiceImpl;
import cn.claredai.user.mapper.SysMenuMapper;
import cn.claredai.user.mapper.SysRoleMenuMapper;
import cn.claredai.user.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Description 菜单-服务实现层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
@Service
public class SysMenuServiceImpl extends SuperServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

//    @Override
//    public List<SysMenu> findOnes() {
//        return null;
//    }

    @Override
    public boolean setMenuToRole(Long roleId, Set<Long> menuIds) {
        sysRoleMenuMapper.delete(roleId, null);

        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> sysRoleMenuMapper.insert(roleId, menuId));
        }
        return true;
    }

    @Override
    public List<SysMenu> findByRoles(Set<Serializable> roleIds) {
        return sysRoleMenuMapper.selectMenusByRoleIds(roleIds, null);
    }

    @Override
    public List<SysMenu> findByRoles(Set<Serializable> roleIds, Integer type) {
        return sysRoleMenuMapper.selectMenusByRoleIds(roleIds, type);
    }

    @Override
    public List<SysMenu> findByRoleCodes(Set<String> roleCodes, Integer type) {
        return sysRoleMenuMapper.selectMenusByRoleCodes(roleCodes, type);
    }
}
