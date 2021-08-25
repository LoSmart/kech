package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsGifts;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsGiftsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帮助表管理
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsGifts")
public class PmsGiftsController {
    @Resource
    private IPmsGiftsService IPmsGiftsService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有帮助表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsGifts:read')")
    public Object getPmsGiftsByPage(PmsGifts entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsGiftsService.page(new Page<PmsGifts>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有帮助表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存帮助表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsGifts:create')")
    public Object savePmsGifts(@RequestBody PmsGifts entity) {
        try {
            if (IPmsGiftsService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存帮助表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新帮助表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGifts:update')")
    public Object updatePmsGifts(@RequestBody PmsGifts entity) {
        try {
            if (IPmsGiftsService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新帮助表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除帮助表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGifts:delete')")
    public Object deletePmsGifts(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("帮助表id");
            }
            if (IPmsGiftsService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除帮助表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给帮助表分配帮助表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGifts:read')")
    public Object getPmsGiftsById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("帮助表id");
            }
            PmsGifts coupon = IPmsGiftsService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询帮助表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除帮助表")
    @PreAuthorize("hasAuthority('pms:PmsGifts:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsGiftsService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
