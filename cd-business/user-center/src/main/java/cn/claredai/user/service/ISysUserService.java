package cn.claredai.user.service;


import cn.claredai.common.core.model.LoginAppUser;
import cn.claredai.common.core.model.SysRole;
import cn.claredai.common.core.model.SysUser;
import cn.claredai.common.core.service.ISuperService;
import cn.claredai.common.core.util.JsonResult;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description 系统用户-服务层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
public interface ISysUserService extends ISuperService<SysUser> {
    /**
     * 获取UserDetails对象
     * @param username
     * @return
     */
    LoginAppUser findByUsername(String username);

    LoginAppUser findByOpenId(String username);

    LoginAppUser findByMobile(String username);

    /**
     * 通过SysUser 转换为 LoginAppUser，把roles和permissions也查询出来
     * @param sysUser
     * @return
     */
    LoginAppUser getLoginAppUser(SysUser sysUser);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);
    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    SysUser selectByMobile(String mobile);
    /**
     * 根据openId查询用户
     * @param openId
     * @return
     */
    SysUser selectByOpenId(String openId);

    /**
     * 用户分配角色
     * @param id
     * @param roleIds
     */
    boolean setRoleToUser(Long id, Set<Long> roleIds);

    /**
     * 更新密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    JsonResult updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 用户列表
     * @param params
     * @return
     */
    PageInfo<SysUser> page(Map<String, Object> params);


    /**
     * 用户角色列表
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 状态变更
     * @param params
     * @return
     */
    JsonResult updateEnabled(Map<String, Object> params);

//    /**
//     * 查询全部用户
//     * @param params
//     * @return
//     */
//    List<SysUserExcel> findAllUsers(Map<String, Object> params);

    boolean saveOrUpdateUser(SysUser sysUser);

    /**
     * 删除用户
     */
    boolean delUser(Long id);
}

