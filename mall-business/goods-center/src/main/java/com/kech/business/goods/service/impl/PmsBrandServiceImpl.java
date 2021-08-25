package com.kech.business.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.business.goods.mapper.PmsBrandMapper;
import com.kech.business.goods.service.IPmsBrandService;
import com.kech.common.entity.pms.PmsBrand;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * 
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {
   @Resource
   private PmsBrandMapper brandMapper;

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatus(showStatus);
        return brandMapper.update(pmsBrand,new QueryWrapper<PmsBrand>().in("id",ids));

    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setFactoryStatus(factoryStatus);
        return brandMapper.update(pmsBrand,new QueryWrapper<PmsBrand>().in("id",ids));
    }
}
