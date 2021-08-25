package com.kech.business.goods.controller;


import com.kech.common.entity.pms.*;
import com.kech.redis.template.RedisRepository;
import com.kech.redis.template.RedisUtil;
import com.kech.business.goods.mapper.PmsProductCategoryMapper;
import com.kech.business.goods.mapper.PmsProductMapper;
import com.kech.business.goods.mapper.PmsSkuStockMapper;
import com.kech.business.goods.service.*;
import com.kech.business.goods.vo.PromotionProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品关系管理
 */
@Slf4j
@RestController
public class NotAuthRpcPmsController {

    @Autowired
    private IPmsProductConsultService pmsProductConsultService;
    @Resource
    private IPmsProductService pmsProductService;
    @Resource
    private IPmsProductAttributeCategoryService productAttributeCategoryService;
    @Resource
    private IPmsProductCategoryService productCategoryService;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IPmsBrandService IPmsBrandService;
    @Resource
    private RedisRepository redisRepository;
    @Resource
    private  PmsProductCategoryMapper categoryMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private IPmsFavoriteService favoriteService;
    @Resource
    private PmsSkuStockMapper skuStockMapper;


    @GetMapping(value = "/notAuth/rpc/PmsProduct/id", params = "id")
    PmsProduct selectById(Long id){
        return  productMapper.selectById(id);
    }

    @GetMapping(value = "/notAuth/rpc/PmsSkuStock/id", params = "id")
    PmsSkuStock selectSkuById(Long id){
        return skuStockMapper.selectById(id);
    }

    @GetMapping(value = "/notAuth/rpc/getPromotionProductList")
    List<PromotionProduct> getPromotionProductList(List<Long> productIdList){
        return  null;
    }

    @PostMapping(value = "/notAuth/rpc/updateSkuById")
    void updateSkuById(PmsSkuStock skuStock){
        skuStockMapper.updateById(skuStock);
    }


}
