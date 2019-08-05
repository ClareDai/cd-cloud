package cn.claredai.zuul.config;

import cn.claredai.common.core.config.DefaultAsyncTaskConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 线程池配置、启用异步
 * @Author ClareDai
 * @Date create in 2019/7/15 22:03
 * @Async quartz 需要使用
 **/
@Configuration
public class AsyncTaskExecutorConfig extends DefaultAsyncTaskConfig {
}
