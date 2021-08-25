package com.kech.business.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.business.goods.vo.ProductAttrInfo;
import com.kech.common.entity.pms.PmsProductAttribute;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 *
 */
public interface IPmsProductAttributeService extends IService<PmsProductAttribute> {

    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);

    boolean saveAndUpdate(PmsProductAttribute entity);
}
