package com.kech.business.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kech.business.user.model.SysPermissionNode;
import com.kech.business.user.service.ISysMenuService;
import com.kech.common.annotation.LoginUser;
import com.kech.common.annotation.SysLog;
import com.kech.common.model.PageResult;
import com.kech.common.model.Result;
import com.kech.common.model.SysPermission;
import com.kech.common.model.SysUser;
import com.kech.common.utils.CommonResult;
import com.kech.common.utils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sys/SysPermission")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有后台用户权限表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sys:SysPermission:read')")
    public Object getRoleByPage(SysPermission entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(menuService.list(new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有后台用户权限表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存后台用户权限表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sys:SysPermission:create')")
    public Object saveRole(@RequestBody SysPermission entity) {
        try {
            entity.setStatus(1);
            entity.setCreateTime(new Date());
            entity.setSort(0);
            if (menuService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "更新后台用户权限表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sys:SysPermission:update')")
    public Object updateRole(@RequestBody SysPermission entity) {
        try {
            if (menuService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除后台用户权限表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sys:SysPermission:delete')")
    public Object deleteRole(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("后台用户权限表id");
            }
            if (menuService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "给后台用户权限表分配后台用户权限表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sys:SysPermission:read')")
    public Object getRoleById(@PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("后台用户权限表id");
            }
            SysPermission coupon = menuService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询后台用户权限表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @SysLog(MODULE = "pms", REMARK = "批量删除后台用户权限表")
    @PreAuthorize("hasAuthority('sys:SysPermission:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = menuService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @GetMapping("/findAlls")
    public PageResult<SysPermission> findAlls() {
        List<SysPermission> list = menuService.findAll();
        return PageResult.<SysPermission>builder().data(list).code(0).count((long) list.size()).build();
    }

    @GetMapping("/findOnes")
    public PageResult<SysPermission> findOnes() {
        List<SysPermission> list = menuService.findOnes();
        return PageResult.<SysPermission>builder().data(list).code(0).count((long) list.size()).build();
    }

    /**
     * 添加菜单 或者 更新
     *
     * @param menu
     * @return
     */
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SysPermission menu) {
        try {
            menuService.saveOrUpdate(menu);
            return Result.succeed("操作成功");
        } catch (Exception ex) {
            log.error("memu-saveOrUpdate-error", ex);
            return Result.failed("操作失败");
        }
    }


    @SysLog(MODULE = "sys", REMARK = "获取所有权限列表")
    @RequestMapping(value = "/findPermissions", method = RequestMethod.GET)
    public Object findPermissions(@LoginUser(isFull = true) SysUser user) {
        Long userId = user.getId();
        return new CommonResult().success(menuService.getPermissionsByUserId(userId));
    }

    @SysLog(MODULE = "sys", REMARK = "以层级结构返回所有权限")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public Object treeList() {
        List<SysPermissionNode> permissionNodeList = menuService.treeList();
        return new CommonResult().success(permissionNodeList);
    }
}
