package com.kech.business.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.business.user.model.SysPermissionNode;
import com.kech.common.model.SysPermission;
import com.kech.common.vo.Tree;

import java.util.List;

/**
 * @author mall
 */
public interface ISysMenuService extends IService<SysPermission> {

	List<Tree<SysPermission>> getPermissionsByUserId(Long id);

	List<SysPermissionNode> treeList();

	int updateShowStatus(List<Long> ids, Integer showStatus);
	/**
	 * 查询所有菜单
	 */
	List<SysPermission> findAll();

	/**
	 * 查询所有一级菜单
	 */
	List<SysPermission> findOnes();



}
