package com.kech.business.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.common.entity.pms.PmsProductFullReduction;
import com.kech.business.goods.mapper.PmsProductFullReductionMapper;
import com.kech.business.goods.service.IPmsProductFullReductionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * 
 */
@Service
public class PmsProductFullReductionServiceImpl extends ServiceImpl<PmsProductFullReductionMapper, PmsProductFullReduction> implements IPmsProductFullReductionService {

}
