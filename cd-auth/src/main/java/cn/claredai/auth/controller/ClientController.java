package cn.claredai.auth.controller;

import cn.claredai.auth.model.Client;
import cn.claredai.auth.service.IClientService;
import cn.claredai.common.core.util.JsonResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 角色相关接口
 * @Author ClareDai
 * @Date create in 2019/6/24 10:23
 **/
@Api(tags = "应用")
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/list")
    @ApiOperation(value = "应用列表")
    public JsonResult list(@RequestParam Map<String, Object> params) {
        PageInfo<Client> pageInfo = clientService.page(params);
        return JsonResult.success(pageInfo);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取应用")
    public JsonResult get(@PathVariable Long id) {
        Client client = clientService.getById(id);
        return JsonResult.success(client);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有应用")
    public JsonResult allClient() {
        List<Client> clients = clientService.list(new QueryWrapper<>());
        return JsonResult.success(clients);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除应用")
    public JsonResult delete(@PathVariable Long id) {
        boolean result = clientService.delClient(id);
        if (!result){
            return JsonResult.fail();
        }
        return JsonResult.success();
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或者修改应用")
    public JsonResult saveOrUpdate(@RequestBody Client clientDto) {
        boolean result =  clientService.saveClient(clientDto);
        if (!result){
            return JsonResult.fail();
        }
        return JsonResult.success();
    }
}
