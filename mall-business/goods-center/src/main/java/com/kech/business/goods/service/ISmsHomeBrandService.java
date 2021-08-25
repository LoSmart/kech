package com.kech.business.goods.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.common.entity.pms.SmsHomeBrand;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 服务类
 * </p>
 *
 * 
 */
public interface ISmsHomeBrandService extends IService<SmsHomeBrand> {
    /**
     * 更新推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
    /**
     * 修改品牌推荐排序
     */
    int updateSort(Long id, Integer sort);
}
