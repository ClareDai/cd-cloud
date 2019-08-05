package cn.claredai.common.redis.lock;

import cn.claredai.common.core.lock.AbstractDistributedLock;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/23 21:33
 **/
@Slf4j
@Component
public class RedisDistributedLock extends AbstractDistributedLock {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                log.debug("get redisDistributeLock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    private boolean setRedis(final String key, final long expire) {
        if(redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(expire))){
            // 对应setnx命令，可以成功设置,也就是key不存在
            return true;
        }

        // 判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String currentLock = redisTemplate.opsForValue().get(key);
        // 如果锁过期 currentLock不为空且小于当前时间
        if(!Strings.isNullOrEmpty(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()){
            // 获取上一个锁的时间value 对应getset，如果lock存在
            String preLock = redisTemplate.opsForValue().getAndSet(key,String.valueOf(expire));

            // 假设两个线程同时进来这里，因为key被占用了，而且锁过期了。获取的值currentLock=A(get取的旧的值肯定是一样的),两个线程的timeStamp都是B,key都是K.锁时间已经过期了。
            // 而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的timeStamp已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if(!Strings.isNullOrEmpty(preLock) && preLock.equals(currentLock) ){
                // preLock不为空且preLock等于currentLock，也就是校验是不是上个对应的商品时间戳，也是防止并发
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key, final long timestamp) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            String value = String.valueOf(timestamp + lockProperties.getTimeoutMillis());
            if(!Strings.isNullOrEmpty(currentValue) && currentValue.equals(value)){
                // 删除锁状态
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("redis分布式锁，解锁异常{}",e);
            return false;
        }
        return true;
    }
}
