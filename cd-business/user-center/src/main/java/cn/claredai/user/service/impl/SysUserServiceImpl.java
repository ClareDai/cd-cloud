package cn.claredai.user.service.impl;

import cn.claredai.common.core.constant.CommonConstant;
import cn.claredai.common.core.enums.UserType;
import cn.claredai.common.core.lock.DistributedLock;
import cn.claredai.common.core.model.*;
import cn.claredai.common.core.service.impl.SuperServiceImpl;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.user.mapper.SysRoleMenuMapper;
import cn.claredai.user.mapper.SysRoleUserMapper;
import cn.claredai.user.mapper.SysUserMapper;
import cn.claredai.user.service.ISysUserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 系统用户-服务实现层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
@Slf4j
@Service
public class SysUserServiceImpl extends SuperServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    private final static String LOCK_KEY_USERNAME = CommonConstant.LOCK_KEY_PREFIX+"username:";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private DistributedLock lock;

    @Override
    public LoginAppUser findByUsername(String username) {
        SysUser sysUser = this.selectByUsername(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser findByOpenId(String username) {
        SysUser sysUser = this.selectByOpenId(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser findByMobile(String username) {
        SysUser sysUser = this.selectByMobile(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser getLoginAppUser(SysUser sysUser) {
        if (sysUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(sysUser, loginAppUser);

            List<SysRole> sysRoles = roleUserMapper.selectRolesByUserId(sysUser.getId());
            // 设置角色
            loginAppUser.setRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Serializable> roleIds = sysRoles.parallelStream().map(SuperModel::getId).collect(Collectors.toSet());
                List<SysMenu> menus = roleMenuMapper.selectMenusByRoleIds(roleIds, null);
                if (!CollectionUtils.isEmpty(menus)) {
                    Set<String> permissions = menus.parallelStream().map(p -> p.getPathMethod()+":"+p.getPath())
                            .collect(Collectors.toSet());
                    // 设置权限集合
                    loginAppUser.setPermissions(permissions);
                }
            }
            return loginAppUser;
        }
        return null;
    }

    @Cacheable(value = "user", key = "#username")
    @Override
    public SysUser selectByUsername(String username) {
        List<SysUser> users = baseMapper.selectList(new QueryWrapper<SysUser>().eq("username", username));
        return getUser(users);
    }

    @Override
    public SysUser selectByMobile(String mobile) {
        List<SysUser> users = baseMapper.selectList(new QueryWrapper<SysUser>().eq("mobile", mobile));
        return getUser(users);
    }

    @Override
    public SysUser selectByOpenId(String openId) {
        List<SysUser> users = baseMapper.selectList(new QueryWrapper<SysUser>().eq("open_id", openId));
        return getUser(users);
    }

    private SysUser getUser(List<SysUser> users) {
        SysUser user = null;
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean setRoleToUser(Long id, Set<Long> roleIds) {
        SysUser sysUser = baseMapper.selectById(id);
        if (sysUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        roleUserMapper.delete(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> roleUserMapper.insert(id, roleId));
        }
        return true;
    }

    @Override
    public JsonResult updatePassword(Long id, String oldPassword, String newPassword) {
        SysUser sysUser = baseMapper.selectById(id);
        if (StrUtil.isNotBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, sysUser.getPassword())) {
                return JsonResult.fail("旧密码错误");
            }
        }
        if (StrUtil.isBlank(newPassword)) {
            newPassword = CommonConstant.DEF_USER_PASSWORD;
        }
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        baseMapper.updateById(user);
        return JsonResult.success();
    }

    @Override
    public PageInfo<SysUser> page(Map<String, Object> params) {
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        params.remove("pageNum");
        params.remove("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = baseMapper.selectList(new QueryWrapper<SysUser>().allEq(params));
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<SysRole> findRolesByUserId(Long userId) {
        return roleUserMapper.selectRolesByUserId(userId);
    }

    @Override
    public JsonResult updateEnabled(Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        Boolean enabled = MapUtils.getBoolean(params, "enabled");

        SysUser appUser = baseMapper.selectById(id);
        if (appUser == null) {
            return JsonResult.fail("用户不存在");
        }
        appUser.setEnabled(enabled);
        appUser.setUpdateTime(new Date());

        int i = baseMapper.updateById(appUser);
        log.info("修改用户：{}", appUser);

        return i > 0 ? JsonResult.success() : JsonResult.fail("更新失败");
    }

    @CachePut(value = "user", key = "#sysUser.username")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateUser(SysUser sysUser) {
        if (sysUser.getId() == null) {
            if (StringUtils.isBlank(sysUser.getType())) {
                sysUser.setType(UserType.BACKEND.name());
            }
            sysUser.setPassword(passwordEncoder.encode(CommonConstant.DEF_USER_PASSWORD));
            sysUser.setEnabled(Boolean.TRUE);
        }
        String username = sysUser.getUsername();
        boolean result = super.saveOrUpdateIdempotency(sysUser, lock
                , LOCK_KEY_USERNAME+username, new QueryWrapper<SysUser>().eq("username", username),username+"已存在");
        //更新角色
        if (result && StrUtil.isNotEmpty(sysUser.getRoleId())) {
            roleUserMapper.delete(sysUser.getId(), null);
            List roleIds = Arrays.asList(sysUser.getRoleId().split(","));
            if (!CollectionUtils.isEmpty(roleIds)) {
                roleIds.forEach(roleId -> roleUserMapper.insert(sysUser.getId(), Long.parseLong(roleId.toString())));
            }
        }
        return result;
    }

    @Override
    public boolean delUser(Long id) {
        roleUserMapper.delete(id, null);
        return baseMapper.deleteById(id) > 0;
    }
}
