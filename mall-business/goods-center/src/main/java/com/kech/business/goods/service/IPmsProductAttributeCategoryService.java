package com.kech.business.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.business.goods.vo.PmsProductAttributeCategoryItem;
import com.kech.common.entity.pms.PmsProductAttributeCategory;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 *
 */
public interface IPmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
