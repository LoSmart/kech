package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsComment;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品评价表管理
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsComment")
public class PmsCommentController {
    @Resource
    private IPmsCommentService IPmsCommentService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有商品评价表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsComment:read')")
    public Object getPmsCommentByPage(PmsComment entity,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsCommentService.page(new Page<PmsComment>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有商品评价表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存商品评价表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsComment:create')")
    public Object savePmsComment(@RequestBody PmsComment entity) {
        try {
            if (IPmsCommentService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存商品评价表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新商品评价表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsComment:update')")
    public Object updatePmsComment(@RequestBody PmsComment entity) {
        try {
            if (IPmsCommentService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新商品评价表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除商品评价表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsComment:delete')")
    public Object deletePmsComment(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("商品评价表id");
            }
            if (IPmsCommentService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除商品评价表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给商品评价表分配商品评价表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsComment:read')")
    public Object getPmsCommentById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("商品评价表id");
            }
            PmsComment coupon = IPmsCommentService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询商品评价表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除商品评价表")
    @PreAuthorize("hasAuthority('pms:PmsComment:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsCommentService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
