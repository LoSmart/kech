package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsAlbumPic;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsAlbumPicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 画册图片表管理
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsAlbumPic")
public class PmsAlbumPicController {
    @Resource
    private IPmsAlbumPicService IPmsAlbumPicService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有画册图片表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsAlbumPic:read')")
    public Object getPmsAlbumPicByPage(PmsAlbumPic entity,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsAlbumPicService.page(new Page<PmsAlbumPic>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有画册图片表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存画册图片表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsAlbumPic:create')")
    public Object savePmsAlbumPic(@RequestBody PmsAlbumPic entity) {
        try {
            if (IPmsAlbumPicService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存画册图片表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新画册图片表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsAlbumPic:update')")
    public Object updatePmsAlbumPic(@RequestBody PmsAlbumPic entity) {
        try {
            if (IPmsAlbumPicService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新画册图片表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除画册图片表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsAlbumPic:delete')")
    public Object deletePmsAlbumPic(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("画册图片表id");
            }
            if (IPmsAlbumPicService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除画册图片表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给画册图片表分配画册图片表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsAlbumPic:read')")
    public Object getPmsAlbumPicById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("画册图片表id");
            }
            PmsAlbumPic coupon = IPmsAlbumPicService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询画册图片表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除画册图片表")
    @PreAuthorize("hasAuthority('pms:PmsAlbumPic:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsAlbumPicService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
