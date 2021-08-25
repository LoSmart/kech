package com.kech.business.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kech.business.user.service.ISysStoreService;
import com.kech.common.model.SysStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/8/7.
 */
@Slf4j
@RestController
@RequestMapping("/notAuth")
public class AppletSysController {

    @Resource
    private ISysStoreService iSysStoreService;

    @GetMapping(value = "/selectStoreById")
    SysStore selectStoreById(Long id){
        return iSysStoreService.getById(id);
    }

    @GetMapping(value = "/selectStoreList")
    List<SysStore> selectStoreList(QueryWrapper<SysStore> sysStoreQueryWrapper){
        return  iSysStoreService.list(sysStoreQueryWrapper);
    }
}
