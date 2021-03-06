package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsGiftsCategory;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsGiftsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帮助分类表管理
 * </p>
 *
 * @author zscat
 * @since 2019-07-07
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsGiftsCategory")
public class PmsGiftsCategoryController {
    @Resource
    private IPmsGiftsCategoryService IPmsGiftsCategoryService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有帮助分类表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsGiftsCategory:read')")
    public Object getPmsGiftsCategoryByPage(PmsGiftsCategory entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsGiftsCategoryService.page(new Page<PmsGiftsCategory>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有帮助分类表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存帮助分类表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsGiftsCategory:create')")
    public Object savePmsGiftsCategory(@RequestBody PmsGiftsCategory entity) {
        try {
            if (IPmsGiftsCategoryService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存帮助分类表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新帮助分类表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGiftsCategory:update')")
    public Object updatePmsGiftsCategory(@RequestBody PmsGiftsCategory entity) {
        try {
            if (IPmsGiftsCategoryService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新帮助分类表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除帮助分类表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGiftsCategory:delete')")
    public Object deletePmsGiftsCategory(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("帮助分类表id");
            }
            if (IPmsGiftsCategoryService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除帮助分类表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给帮助分类表分配帮助分类表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGiftsCategory:read')")
    public Object getPmsGiftsCategoryById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("帮助分类表id");
            }
            PmsGiftsCategory coupon = IPmsGiftsCategoryService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询帮助分类表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除帮助分类表")
    @PreAuthorize("hasAuthority('pms:PmsGiftsCategory:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsGiftsCategoryService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
