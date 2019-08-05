package cn.claredai.user.mapper;

import cn.claredai.common.core.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 用户角色关联表-数据层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
@Repository
public interface SysRoleUserMapper {
    int delete(@Param("userId") Serializable userId, @Param("roleId") Serializable roleId);

    int insert(@Param("userId") Serializable userId, @Param("roleId") Serializable roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<SysRole> selectRolesByUserId(Serializable userId);
}
