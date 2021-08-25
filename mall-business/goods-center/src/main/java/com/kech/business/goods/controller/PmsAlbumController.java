package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsAlbum;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 相册表管理
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsAlbum")
public class PmsAlbumController {
    @Resource
    private IPmsAlbumService IPmsAlbumService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有相册表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsAlbum:read')")
    public Object getPmsAlbumByPage(PmsAlbum entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsAlbumService.page(new Page<PmsAlbum>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有相册表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存相册表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsAlbum:create')")
    public Object savePmsAlbum(@RequestBody PmsAlbum entity) {
        try {
            if (IPmsAlbumService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存相册表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新相册表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsAlbum:update')")
    public Object updatePmsAlbum(@RequestBody PmsAlbum entity) {
        try {
            if (IPmsAlbumService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新相册表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除相册表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsAlbum:delete')")
    public Object deletePmsAlbum(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("相册表id");
            }
            if (IPmsAlbumService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除相册表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给相册表分配相册表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsAlbum:read')")
    public Object getPmsAlbumById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("相册表id");
            }
            PmsAlbum coupon = IPmsAlbumService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询相册表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除相册表")
    @PreAuthorize("hasAuthority('pms:PmsAlbum:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsAlbumService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
