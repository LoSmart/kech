package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsProductAttributeCategory;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsProductAttributeCategoryService;
import com.kech.business.goods.vo.PmsProductAttributeCategoryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 产品属性分类表管理
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsProductAttributeCategory")
public class PmsProductAttributeCategoryController {
    @Resource
    private IPmsProductAttributeCategoryService IPmsProductAttributeCategoryService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有产品属性分类表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsProductAttributeCategory:read')")
    public Object getPmsProductAttributeCategoryByPage(PmsProductAttributeCategory entity,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsProductAttributeCategoryService.page(new Page<PmsProductAttributeCategory>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有产品属性分类表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存产品属性分类表")
    @PostMapping(value = "/create")
    public Object create(@RequestParam String name) {
        try {
            PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
            productAttributeCategory.setName(name);
            if (IPmsProductAttributeCategoryService.save(productAttributeCategory)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存产品属性分类表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新产品属性分类表")
    @PostMapping(value = "/update/{id}")
    public Object updatePmsProductAttributeCategory(@PathVariable Long id, @RequestParam String name) {
        try {
            PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
            productAttributeCategory.setName(name);
            productAttributeCategory.setId(id);
            if (IPmsProductAttributeCategoryService.updateById(productAttributeCategory)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新产品属性分类表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除产品属性分类表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductAttributeCategory:delete')")
    public Object deletePmsProductAttributeCategory(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("产品属性分类表id");
            }
            if (IPmsProductAttributeCategoryService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除产品属性分类表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给产品属性分类表分配产品属性分类表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductAttributeCategory:read')")
    public Object getPmsProductAttributeCategoryById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("产品属性分类表id");
            }
            PmsProductAttributeCategory coupon = IPmsProductAttributeCategoryService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询产品属性分类表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除产品属性分类表")
    @PreAuthorize("hasAuthority('pms:PmsProductAttributeCategory:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsProductAttributeCategoryService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "pms", REMARK = "获取所有商品属性分类及其下属性")
    @RequestMapping(value = "/list/withAttr", method = RequestMethod.GET)
    @ResponseBody
    public Object getListWithAttr() {
        List<PmsProductAttributeCategoryItem> productAttributeCategoryResultList = IPmsProductAttributeCategoryService.getListWithAttr();
        return new CommonResult().success(productAttributeCategoryResultList);
    }
}
