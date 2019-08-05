package cn.claredai.common.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/23 23:10
 **/
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "cd.distributed-lock")
public class DistributedLockProperties {
    /**
     * 超时时间
     */
    private long timeoutMillis = 5000;
    /**
     * 重试次数
     */
    private int retryTimes = 100;
    /**
     * 每次重试后等待的时间
     */
    private long sleepMillis = 100;
}
