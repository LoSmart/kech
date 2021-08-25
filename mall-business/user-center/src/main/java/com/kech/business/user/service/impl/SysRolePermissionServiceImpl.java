package com.kech.business.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.common.model.SysRoleMenu;
import com.kech.business.user.mapper.SysRolePermissionMapper;
import com.kech.business.user.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色和权限关系表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRoleMenu> implements ISysRolePermissionService {

}
