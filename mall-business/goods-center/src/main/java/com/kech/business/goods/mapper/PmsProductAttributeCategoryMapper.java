package com.kech.business.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.entity.pms.PmsProductAttributeCategory;
import com.kech.business.goods.vo.PmsProductAttributeCategoryItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface PmsProductAttributeCategoryMapper extends BaseMapper<PmsProductAttributeCategory> {

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
