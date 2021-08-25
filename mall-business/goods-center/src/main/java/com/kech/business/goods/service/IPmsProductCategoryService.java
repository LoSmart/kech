package com.kech.business.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.business.goods.vo.PmsProductCategoryWithChildrenItem;
import com.kech.common.entity.pms.PmsProductCategory;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * 
 */
public interface IPmsProductCategoryService extends IService<PmsProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listWithChildren();

    int updateNavStatus(List<Long> ids, Integer navStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    boolean updateAnd(PmsProductCategory entity);

    boolean saveAnd(PmsProductCategory entity);
}
