package cn.claredai.common.core.lock;

import cn.claredai.common.core.properties.DistributedLockProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 分布式锁抽象类
 * @Author ClareDai
 * @Date create in 2019/6/4 14:21
 **/
public abstract class AbstractDistributedLock implements DistributedLock{
    @Autowired
    protected DistributedLockProperties lockProperties;

    @Override
    public boolean lock(String key, long timestamp) {
        return lock(key, timestamp + lockProperties.getTimeoutMillis(), lockProperties.getRetryTimes(), lockProperties.getSleepMillis());
    }

    @Override
    public boolean lock(String key, long timestamp, long sleepMillis) {
        return lock(key, timestamp + lockProperties.getTimeoutMillis(), lockProperties.getRetryTimes(), sleepMillis);
    }

    @Override
    public boolean lock(String key, long timestamp, int retryTimes) {
        return lock(key, timestamp + lockProperties.getTimeoutMillis(), retryTimes, lockProperties.getSleepMillis());
    }
}
