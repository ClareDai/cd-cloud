package cn.claredai.zuul.feign.fallback;

import cn.claredai.common.core.util.JsonResult;
import cn.claredai.zuul.feign.MenuService;
import cn.hutool.core.collection.CollectionUtil;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/15 22:13
 **/
@Slf4j
@Component
public class MenuServiceFallbackFactory implements FallbackFactory<MenuService> {
    @Override
    public MenuService create(Throwable throwable) {
        return roleIds -> {
            log.error("调用findByRoleCodes异常：{}", roleIds, throwable);
            return JsonResult.success(CollectionUtil.newArrayList());
        };
    }
}
