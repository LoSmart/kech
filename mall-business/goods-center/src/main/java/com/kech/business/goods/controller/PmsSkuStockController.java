package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsSkuStock;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * sku的库存管理
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsSkuStock")
public class PmsSkuStockController {
    @Resource
    private IPmsSkuStockService IPmsSkuStockService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有sku的库存列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsSkuStock:read')")
    public Object getPmsSkuStockByPage(PmsSkuStock entity,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsSkuStockService.page(new Page<PmsSkuStock>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有sku的库存列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存sku的库存")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsSkuStock:create')")
    public Object savePmsSkuStock(@RequestBody PmsSkuStock entity) {
        try {
            if (IPmsSkuStockService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存sku的库存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新sku的库存")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsSkuStock:update')")
    public Object updatePmsSkuStock(@RequestBody PmsSkuStock entity) {
        try {
            if (IPmsSkuStockService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新sku的库存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除sku的库存")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsSkuStock:delete')")
    public Object deletePmsSkuStock(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("sku的库存id");
            }
            if (IPmsSkuStockService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除sku的库存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    //根据Id查询
    @GetMapping(value = "/id", params = "id")
    public PmsSkuStock findById(Long id) {
        return IPmsSkuStockService.getById(id);
    }

    @SysLog(MODULE = "pms", REMARK = "给sku的库存分配sku的库存")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsSkuStock:read')")
    public Object getPmsSkuStockById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("sku的库存id");
            }
            PmsSkuStock coupon = IPmsSkuStockService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询sku的库存明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除sku的库存")
    @PreAuthorize("hasAuthority('pms:PmsSkuStock:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsSkuStockService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
    @SysLog(MODULE = "pms", REMARK = "根据商品编号及编号模糊搜索sku库存")
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public Object getList(@PathVariable Long pid, @RequestParam(value = "keyword", required = false) String keyword) {
        List<PmsSkuStock> skuStockList = IPmsSkuStockService.getList(pid, keyword);
        return new CommonResult().success(skuStockList);
    }

    @SysLog(MODULE = "pms", REMARK = "批量更新库存信息")
    @RequestMapping(value = "/update/{pid}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long pid, @RequestBody List<PmsSkuStock> skuStockList) {
        int count = IPmsSkuStockService.update(pid, skuStockList);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
}
