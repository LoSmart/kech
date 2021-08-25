package com.kech.common.vo;


import com.kech.common.entity.cms.CmsSubject;
import com.kech.common.entity.pms.PmsBrand;
import com.kech.common.entity.pms.PmsProduct;
import com.kech.common.entity.pms.PmsProductAttributeCategory;
import com.kech.common.entity.sms.SmsHomeAdvertise;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回信息封装
 */
@Getter
@Setter
public class HomeContentResult {
    //轮播广告
    private List<SmsHomeAdvertise> advertiseList;
    //推荐品牌
    private List<PmsBrand> brandList;

    //新品推荐
    private List<PmsProduct> newProductList;
    //人气推荐
    private List<PmsProduct> hotProductList;
    //推荐专题
    private List<CmsSubject> subjectList;

    private List<PmsProductAttributeCategory> cat_list;
}
