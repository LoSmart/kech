package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsProductLadder;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsProductLadderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品)管理
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsProductLadder")
public class PmsProductLadderController {
    @Resource
    private IPmsProductLadderService IPmsProductLadderService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有产品阶梯价格表(只针对同商品)列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsProductLadder:read')")
    public Object getPmsProductLadderByPage(PmsProductLadder entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsProductLadderService.page(new Page<PmsProductLadder>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有产品阶梯价格表(只针对同商品)列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存产品阶梯价格表(只针对同商品)")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsProductLadder:create')")
    public Object savePmsProductLadder(@RequestBody PmsProductLadder entity) {
        try {
            if (IPmsProductLadderService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存产品阶梯价格表(只针对同商品)：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新产品阶梯价格表(只针对同商品)")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductLadder:update')")
    public Object updatePmsProductLadder(@RequestBody PmsProductLadder entity) {
        try {
            if (IPmsProductLadderService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新产品阶梯价格表(只针对同商品)：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除产品阶梯价格表(只针对同商品)")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductLadder:delete')")
    public Object deletePmsProductLadder(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("产品阶梯价格表(只针对同商品)id");
            }
            if (IPmsProductLadderService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除产品阶梯价格表(只针对同商品)：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给产品阶梯价格表(只针对同商品)分配产品阶梯价格表(只针对同商品)")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductLadder:read')")
    public Object getPmsProductLadderById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("产品阶梯价格表(只针对同商品)id");
            }
            PmsProductLadder coupon = IPmsProductLadderService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询产品阶梯价格表(只针对同商品)明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除产品阶梯价格表(只针对同商品)")
    @PreAuthorize("hasAuthority('pms:PmsProductLadder:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsProductLadderService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
