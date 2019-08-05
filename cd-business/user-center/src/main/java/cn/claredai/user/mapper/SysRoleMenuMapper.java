package cn.claredai.user.mapper;

import cn.claredai.common.core.model.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Description 角色菜单-数据层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
@Repository
public interface SysRoleMenuMapper {
    int insert(@Param("roleId") Serializable roleId, @Param("menuId") Serializable menuId);

    int delete(@Param("roleId") Serializable roleId, @Param("menuId") Serializable menuId);

    List<SysMenu> selectMenusByRoleIds(@Param("roleIds") Set<Serializable> roleIds, @Param("type") Integer type);

    List<SysMenu> selectMenusByRoleCodes(@Param("roleCodes") Set<String> roleCodes, @Param("type") Integer type);
}
