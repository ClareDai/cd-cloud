package cn.claredai.user.service.impl;

import cn.claredai.common.core.constant.CommonConstant;
import cn.claredai.common.core.lock.DistributedLock;
import cn.claredai.common.core.model.SysRole;
import cn.claredai.common.core.service.impl.SuperServiceImpl;
import cn.claredai.user.mapper.SysRoleMapper;
import cn.claredai.user.mapper.SysRoleMenuMapper;
import cn.claredai.user.mapper.SysRoleUserMapper;
import cn.claredai.user.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description 系统角色-服务实现层
 * @Author ClareDai
 * @Date create in 2019-06-29 09:42:33
 **/
@Service
public class SysRoleServiceImpl extends SuperServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    private final static String LOCK_KEY_ROLECODE = CommonConstant.LOCK_KEY_PREFIX+"rolecode:";

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRole(SysRole sysRole) {
        String roleCode = sysRole.getCode();
        return super.saveIdempotency(sysRole, lock, LOCK_KEY_ROLECODE+roleCode, new QueryWrapper<SysRole>().eq("code", roleCode),"角色code已存在");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRole(Long id) {
        baseMapper.deleteById(id);
        roleMenuMapper.delete(id, null);
        roleUserMapper.delete(null, id);
        return true;
    }

    @Override
    public PageInfo<SysRole> page(Map<String, Object> params) {
        int pageNum = MapUtils.getInteger(params, "pageNum");
        int pageSize = MapUtils.getInteger(params, "pageSize");
        params.remove("pageNum");
        params.remove("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> list = baseMapper.selectList(new QueryWrapper<SysRole>().allEq(params));
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
