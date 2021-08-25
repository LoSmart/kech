package com.kech.business.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.model.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户角色和权限关系表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRoleMenu> {

}
