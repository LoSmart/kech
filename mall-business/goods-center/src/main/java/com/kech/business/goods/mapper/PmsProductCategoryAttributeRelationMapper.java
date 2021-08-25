package com.kech.business.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.entity.pms.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类） Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface PmsProductCategoryAttributeRelationMapper extends BaseMapper<PmsProductCategoryAttributeRelation> {

}
