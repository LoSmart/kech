package com.kech.business.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kech.common.entity.pms.PmsProduct;
import com.kech.business.goods.vo.PmsProductResult;
import com.kech.business.goods.vo.PromotionProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface PmsProductMapper extends BaseMapper<PmsProduct> {

   // CartProduct getCartProduct(@Param("id") Long id);

    List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids);

    PmsProductResult getUpdateInfo(Long id);
}
