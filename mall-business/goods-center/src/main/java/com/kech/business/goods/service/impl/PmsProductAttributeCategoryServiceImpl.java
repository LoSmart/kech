package com.kech.business.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.business.goods.mapper.PmsProductAttributeCategoryMapper;
import com.kech.business.goods.service.IPmsProductAttributeCategoryService;
import com.kech.business.goods.vo.PmsProductAttributeCategoryItem;
import com.kech.common.entity.pms.PmsProductAttributeCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * 
 */
@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements IPmsProductAttributeCategoryService {

    @Resource
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        return productAttributeCategoryMapper.getListWithAttr();
    }
}
