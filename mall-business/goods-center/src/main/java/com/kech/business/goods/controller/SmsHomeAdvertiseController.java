package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.sms.SmsHomeAdvertise;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.ISmsHomeAdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页轮播广告表管理
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/marking/SmsHomeAdvertise")
public class SmsHomeAdvertiseController {
    @Resource
    private ISmsHomeAdvertiseService ISmsHomeAdvertiseService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有首页轮播广告表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('marking:SmsHomeAdvertise:read')")
    public Object getSmsHomeAdvertiseByPage(SmsHomeAdvertise entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsHomeAdvertiseService.page(new Page<SmsHomeAdvertise>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有首页轮播广告表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存首页轮播广告表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('marking:SmsHomeAdvertise:create')")
    public Object saveSmsHomeAdvertise(@RequestBody SmsHomeAdvertise entity) {
        try {
            entity.setClickCount(0);
            entity.setOrderCount(0);
            if (ISmsHomeAdvertiseService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存首页轮播广告表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新首页轮播广告表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeAdvertise:update')")
    public Object updateSmsHomeAdvertise(@RequestBody SmsHomeAdvertise entity) {
        try {
            if (ISmsHomeAdvertiseService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新首页轮播广告表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除首页轮播广告表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeAdvertise:delete')")
    public Object deleteSmsHomeAdvertise(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("首页轮播广告表id");
            }
            if (ISmsHomeAdvertiseService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除首页轮播广告表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给首页轮播广告表分配首页轮播广告表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeAdvertise:read')")
    public Object getSmsHomeAdvertiseById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("首页轮播广告表id");
            }
            SmsHomeAdvertise coupon = ISmsHomeAdvertiseService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询首页轮播广告表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除首页轮播广告表")
    @PreAuthorize("hasAuthority('marking:SmsHomeAdvertise:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsHomeAdvertiseService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
    @SysLog(MODULE = "sms", REMARK = "修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateStatus(@PathVariable Long id, Integer status) {
        SmsHomeAdvertise record = new SmsHomeAdvertise();
        record.setId(id);
        record.setStatus(status);
        return ISmsHomeAdvertiseService.updateById(record);

    }



}
