package cn.claredai.auth.service.impl;

import cn.claredai.auth.mapper.ClientMapper;
import cn.claredai.auth.model.Client;
import cn.claredai.auth.service.IClientService;
import cn.claredai.common.core.constant.CommonConstant;
import cn.claredai.common.core.constant.SecurityConstants;
import cn.claredai.common.core.lock.DistributedLock;
import cn.claredai.common.core.service.impl.SuperServiceImpl;
import cn.claredai.common.redis.repository.RedisRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/21 10:21
 **/
@Service
public class ClientServiceImpl extends SuperServiceImpl<ClientMapper, Client> implements IClientService {
    private final static String LOCK_KEY_CLIENTID = CommonConstant.LOCK_KEY_PREFIX+"clientId:";

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DistributedLock lock;

    @Override
    public boolean saveClient(Client client) {
        client.setClientSecret(passwordEncoder.encode(client.getClientSecretStr()));
        String clientId = client.getClientId();
        boolean result = super.saveOrUpdateIdempotency(client, lock,
                LOCK_KEY_CLIENTID + clientId,
                new QueryWrapper<Client>().eq("client_id", clientId),
                clientId + "已存在");
        // 写入redis缓存
        redisRepository.set(clientRedisKey(client.getClientId()), client);
        return result;
    }

    @Override
    public PageInfo<Client> page(Map<String, Object> params) {
        QueryWrapper<Client> queryWrapper = new QueryWrapper();
        queryWrapper.like(false,"client_id", MapUtils.getString(params, "clientId"));
        PageHelper.startPage(MapUtils.getInteger(params, "pageNum"), MapUtils.getInteger(params, "pageSize"));
        List<Client> list = baseMapper.selectList(queryWrapper);
        PageInfo<Client> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean delClient(long id) {
        String clientId = baseMapper.selectById(id).getClientId();
        baseMapper.deleteById(id);
        redisRepository.del(clientRedisKey(clientId));
        return true;
    }

    private String clientRedisKey(String clientId) {
        return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
    }
}
