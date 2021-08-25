package com.kech.business.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kech.common.entity.pms.PmsBrand;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 *
 */
public interface IPmsBrandService extends IService<PmsBrand> {

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
