package com.kech.business.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.business.user.service.ISysAdminLogService;
import com.kech.common.annotation.SysLog;
import com.kech.common.model.SysAdminLog;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.common.vo.LogParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@RequestMapping("/sys/SysAdminLog")
public class SysAdminLogController {
    @Resource
    private ISysAdminLogService iSysAdminLogService;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询接口统计")
    @GetMapping(value = "/logStatic")
    public Object logStatic(LogParam entity
    ) {
        try {
            return new CommonResult().success(iSysAdminLogService.selectPageExt(entity));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sys:SysAdminLog:read')")
    public Object getRoleByPage(SysAdminLog entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(iSysAdminLogService.page(new Page<SysAdminLog>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sys:SysAdminLog:create')")
    public Object saveRole(@RequestBody SysAdminLog entity) {
        try {
            if (iSysAdminLogService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "更新")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sys:SysAdminLog:update')")
    public Object updateRole(@RequestBody SysAdminLog entity) {
        try {
            if (iSysAdminLogService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sys:SysAdminLog:delete')")
    public Object deleteRole(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            if (iSysAdminLogService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "给分配")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sys:SysAdminLog:read')")
    public Object getRoleById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            SysAdminLog coupon = iSysAdminLogService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除")
    @PreAuthorize("hasAuthority('sys:SysAdminLog:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iSysAdminLogService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
