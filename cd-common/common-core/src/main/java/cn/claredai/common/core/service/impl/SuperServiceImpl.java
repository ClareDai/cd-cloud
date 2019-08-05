package cn.claredai.common.core.service.impl;

import cn.claredai.common.core.exception.IdempotencyException;
import cn.claredai.common.core.exception.LockException;
import cn.claredai.common.core.lock.DistributedLock;
import cn.claredai.common.core.model.SuperModel;
import cn.claredai.common.core.service.ISuperService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/23 22:41
 **/
public class SuperServiceImpl<M extends BaseMapper<T>, T extends SuperModel> extends ServiceImpl<M, T> implements ISuperService<T> {

    /**
     * 幂等性新增记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , msg);
     */
    @Override
    public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (lock == null) {
            throw new LockException("DistributedLock is null");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }
        final long timestamp = System.currentTimeMillis();
        try {
            //加锁
            boolean isLock = lock.lock(lockKey, timestamp);
            if (isLock) {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0) {
                    return super.save(entity);
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    throw new IdempotencyException(msg);
                }
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            lock.releaseLock(lockKey, timestamp);
        }
    }

    @Override
    public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
        return saveIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else {
                    return updateById(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
        return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper,null);
    }
}
