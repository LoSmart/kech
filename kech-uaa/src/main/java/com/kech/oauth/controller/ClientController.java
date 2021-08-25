package com.kech.oauth.controller;

import com.google.common.collect.Maps;
import com.kech.common.model.PageResult;
import com.kech.common.model.Result;
import com.kech.oauth.dto.ClientDto;
import com.kech.oauth.model.Client;
import com.kech.oauth.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色相关接口
 *
 * @author mall
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService clientService;

    //应用列表
    @GetMapping
    public PageResult<Client> list(@RequestParam Map<String, Object> params) {
        return clientService.listClent(params, true);
    }

    //根据id获取应用
    @GetMapping("/detail/{id}")
    public Client get(@PathVariable Long id) {
        return clientService.getById(id);
    }

    //所有应用
    @GetMapping("/all")
    public List<Client> allClient() {
        PageResult<Client> page = clientService.listClent(Maps.newHashMap(), false);
        return page.getData();
    }

    //删除应用
    @GetMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delClient(id);
    }

    //保存或者修改应用
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ClientDto clientDto) {
        return clientService.saveClient(clientDto);
    }
}
