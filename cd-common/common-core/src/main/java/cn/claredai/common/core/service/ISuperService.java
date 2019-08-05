package cn.claredai.common.core.service;

import cn.claredai.common.core.lock.DistributedLock;
import cn.claredai.common.core.model.SuperModel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/23 22:31
 **/
public interface ISuperService<T extends SuperModel> extends IService<T> {
    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);

}
