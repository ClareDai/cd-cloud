package cn.claredai.user.service;

import cn.claredai.common.core.model.SysMenu;
import cn.claredai.common.core.service.ISuperService;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description 菜单-服务层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
public interface ISysMenuService extends ISuperService<SysMenu> {
//    /**
//     * 查询所有一级菜单
//     */
//    List<SysMenu> findOnes();

    /**
     * 角色分配菜单
     * @param roleId
     * @param menuIds
     */
    boolean setMenuToRole(Long roleId, Set<Long> menuIds);

    /**
     * 角色菜单列表
     * @param roleIds 角色ids
     * @return
     */
    List<SysMenu> findByRoles(Set<Serializable> roleIds);

    /**
     * 角色菜单列表
     * @param roleIds 角色ids
     * @param roleIds 是否菜单
     * @return
     */
    List<SysMenu> findByRoles(Set<Serializable> roleIds, Integer type);

    /**
     * 角色菜单列表
     * @param roleCodes
     * @return
     */
    List<SysMenu> findByRoleCodes(Set<String> roleCodes, Integer type);

//    PageInfo<SysMenu> page(Map<String, Object> params);
}

