package cn.claredai.auth.service;

import cn.claredai.auth.model.Client;
import cn.claredai.common.core.service.ISuperService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/20 23:44
 **/
public interface IClientService extends ISuperService<Client> {
    boolean saveClient(Client clientDto);

    /**
     * 查询应用列表
     * @param params
     */
    PageInfo<Client> page(Map<String, Object> params);

    boolean delClient(long id);
}
