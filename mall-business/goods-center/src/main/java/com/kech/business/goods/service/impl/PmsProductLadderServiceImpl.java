package com.kech.business.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.business.goods.mapper.PmsProductLadderMapper;
import com.kech.business.goods.service.IPmsProductLadderService;
import com.kech.common.entity.pms.PmsProductLadder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) 服务实现类
 * </p>
 *
 */
@Service
public class PmsProductLadderServiceImpl extends ServiceImpl<PmsProductLadderMapper, PmsProductLadder> implements IPmsProductLadderService {

}
