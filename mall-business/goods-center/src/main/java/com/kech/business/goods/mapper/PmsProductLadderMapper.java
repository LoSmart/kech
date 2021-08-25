package com.kech.business.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.entity.pms.PmsProductLadder;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface PmsProductLadderMapper extends BaseMapper<PmsProductLadder> {

}
