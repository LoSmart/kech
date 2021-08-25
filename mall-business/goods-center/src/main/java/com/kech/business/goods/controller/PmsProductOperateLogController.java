package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsProductOperateLog;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsProductOperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 管理
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsProductOperateLog")
public class PmsProductOperateLogController {
    @Resource
    private IPmsProductOperateLogService IPmsProductOperateLogService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsProductOperateLog:read')")
    public Object getPmsProductOperateLogByPage(PmsProductOperateLog entity,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsProductOperateLogService.page(new Page<PmsProductOperateLog>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsProductOperateLog:create')")
    public Object savePmsProductOperateLog(@RequestBody PmsProductOperateLog entity) {
        try {
            if (IPmsProductOperateLogService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductOperateLog:update')")
    public Object updatePmsProductOperateLog(@RequestBody PmsProductOperateLog entity) {
        try {
            if (IPmsProductOperateLogService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductOperateLog:delete')")
    public Object deletePmsProductOperateLog(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            if (IPmsProductOperateLogService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给分配")
    //查询明细
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProductOperateLog:read')")
    public Object getPmsProductOperateLogById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            PmsProductOperateLog coupon = IPmsProductOperateLogService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除")
    @PreAuthorize("hasAuthority('pms:PmsProductOperateLog:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsProductOperateLogService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
