package com.kech.business.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kech.common.annotation.SysLog;
import com.kech.common.entity.pms.PmsBrand;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import com.kech.business.goods.service.IPmsBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌表
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/pms/PmsBrand")
public class PmsBrandController {
    @Resource
    private IPmsBrandService IPmsBrandService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有品牌表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsBrand:read')")
    public Object getPmsBrandByPage(PmsBrand entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsBrandService.page(new Page<PmsBrand>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有品牌表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存品牌表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsBrand:create')")
    public Object savePmsBrand(@RequestBody PmsBrand entity) {
        try {
            if (IPmsBrandService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存品牌表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新品牌表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsBrand:update')")
    public Object updatePmsBrand(@RequestBody PmsBrand entity) {
        try {
            if (IPmsBrandService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新品牌表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除品牌表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsBrand:delete')")
    public Object deletePmsBrand(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("品牌表id");
            }
            if (IPmsBrandService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除品牌表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给品牌表分配品牌表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsBrand:read')")
    public Object getPmsBrandById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("品牌表id");
            }
            PmsBrand coupon = IPmsBrandService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询品牌表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    //批量删除品牌表
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除品牌表")
    @PreAuthorize("hasAuthority('pms:PmsBrand:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsBrandService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
    //批量更新显示状态
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量更新显示状态")
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public Object updateShowStatus(@RequestParam("ids") List<Long> ids,
                                   @RequestParam("showStatus") Integer showStatus) {
        int count = IPmsBrandService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @RequestMapping(value = "/update/factoryStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量更新厂家制造商状态")
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public Object updateFactoryStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("factoryStatus") Integer factoryStatus) {
        int count = IPmsBrandService.updateFactoryStatus(ids, factoryStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
}
