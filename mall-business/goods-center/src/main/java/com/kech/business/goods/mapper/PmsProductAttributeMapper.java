package com.kech.business.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.entity.pms.PmsProductAttribute;
import com.kech.business.goods.vo.ProductAttrInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface PmsProductAttributeMapper extends BaseMapper<PmsProductAttribute> {

    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
