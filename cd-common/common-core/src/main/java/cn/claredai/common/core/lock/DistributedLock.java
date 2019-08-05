package cn.claredai.common.core.lock;

/**
 * @Description 分布式锁顶级接口
 * 例如：
 * RETRY_TIMES=100，SLEEP_MILLIS=100
 * RETRY_TIMES * SLEEP_MILLIS = 10000 意味着如果一直获取不了锁，最长会等待10秒后抛超时异常
 * @Author ClareDai
 * @Date create in 2019/6/4 14:17
 **/
public interface DistributedLock {

    /**
     * 获取锁
     *
     * @param key    key
     * @param timestamp 当前时间戳
     * @return 成功/失败
     */
    boolean lock(String key, long timestamp);

    /**
     * 获取锁
     *
     * @param key         key
     * @param timestamp   当前时间戳
     * @param sleepMillis 获取锁失败的重试间隔
     * @return 成功/失败
     */
    boolean lock(String key, long timestamp, long sleepMillis);

    /**
     * 获取锁
     *
     * @param key        key
     * @param timestamp  当前时间戳
     * @param retryTimes 重试次数
     * @return 成功/失败
     */
    boolean lock(String key, long timestamp, int retryTimes);

    /**
     * 获取锁
     *
     * @param key         key
     * @param expire      获取锁超时时间(当前时间戳+超时时长)
     * @param retryTimes  重试次数
     * @param sleepMillis 获取锁失败的重试间隔
     * @return 成功/失败
     */
    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    /**
     * 释放锁
     *
     * @param key     key值
     * @param timestamp  当前时间戳
     * @return 释放结果
     */
    boolean releaseLock(String key, long timestamp);
}
