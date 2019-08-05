package cn.claredai.common.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description mapper 父类，注意这个类不要让 mp 扫描到！！
 * @Author ClareDai
 * @Date create in 2019/7/5 16:19
 **/
public interface SuperMapper <T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法
}
