package com.kech.business.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.entity.pms.PmsProductFullReduction;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品满减表(只针对同商品) Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface PmsProductFullReductionMapper extends BaseMapper<PmsProductFullReduction> {

}
