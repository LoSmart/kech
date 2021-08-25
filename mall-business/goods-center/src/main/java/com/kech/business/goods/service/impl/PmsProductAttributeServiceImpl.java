package com.kech.business.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kech.common.entity.pms.PmsProductAttribute;
import com.kech.common.entity.pms.PmsProductAttributeCategory;
import com.kech.business.goods.mapper.PmsProductAttributeCategoryMapper;
import com.kech.business.goods.mapper.PmsProductAttributeMapper;
import com.kech.business.goods.service.IPmsProductAttributeService;
import com.kech.business.goods.vo.ProductAttrInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * 
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements IPmsProductAttributeService {

    @Resource
    private  PmsProductAttributeMapper productAttributeMapper;
    @Resource
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return productAttributeMapper.getProductAttrInfo(productCategoryId);
    }

    @Transactional
    @Override
    public boolean saveAndUpdate(PmsProductAttribute entity) {
         productAttributeMapper.insert(entity);
        //新增商品属性以后需要更新商品属性分类数量
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectById(entity.getProductAttributeCategoryId());
        if (entity.getType() == 0) {
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
        } else if (entity.getType() == 1) {
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
        }
        productAttributeCategoryMapper.updateById(pmsProductAttributeCategory);
        return true;
    }
}
