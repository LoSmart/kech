package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.SmsHomeRecommendProduct;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.ISmsHomeRecommendProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 人气推荐商品表管理
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/marking/SmsHomeRecommendProduct")
public class SmsHomeRecommendProductController {
    @Resource
    private ISmsHomeRecommendProductService ISmsHomeRecommendProductService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有人气推荐商品表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendProduct:read')")
    public Object getSmsHomeRecommendProductByPage(SmsHomeRecommendProduct entity,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsHomeRecommendProductService.page(new Page<SmsHomeRecommendProduct>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有人气推荐商品表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sms", REMARK = "更新人气推荐商品表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendProduct:update')")
    public Object updateSmsHomeRecommendProduct(@RequestBody SmsHomeRecommendProduct entity) {
        try {
            if (ISmsHomeRecommendProductService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新人气推荐商品表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除人气推荐商品表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendProduct:delete')")
    public Object deleteSmsHomeRecommendProduct(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("人气推荐商品表id");
            }
            if (ISmsHomeRecommendProductService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除人气推荐商品表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给人气推荐商品表分配人气推荐商品表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendProduct:read')")
    public Object getSmsHomeRecommendProductById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("人气推荐商品表id");
            }
            SmsHomeRecommendProduct coupon = ISmsHomeRecommendProductService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询人气推荐商品表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除人气推荐商品表")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendProduct:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsHomeRecommendProductService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
    @PostMapping(value = "/create")
    @ResponseBody
    public Object create(@RequestBody List<SmsHomeRecommendProduct> homeBrandList) {
        boolean count = ISmsHomeRecommendProductService.saveBatch(homeBrandList);
        if (count ) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    //修改推荐排序
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSort(@PathVariable Long id, Integer sort) {
        int count = ISmsHomeRecommendProductService.updateSort(id, sort);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
    //批量修改推荐状态
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = ISmsHomeRecommendProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
