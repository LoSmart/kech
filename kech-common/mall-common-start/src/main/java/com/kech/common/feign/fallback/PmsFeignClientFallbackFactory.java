package com.kech.common.feign.fallback;

import com.kech.common.entity.pms.PmsGifts;
import com.kech.common.entity.pms.PmsProduct;
import com.kech.common.entity.pms.PmsProductConsult;
import com.kech.common.entity.pms.PmsSkuStock;
import com.kech.common.feign.PmsFeignClinent;
import com.kech.common.vo.PromotionProduct;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * userService降级工场
 *
 * @author mall
 * @date 2019/1/18
 */
@Slf4j
@Component
public class PmsFeignClientFallbackFactory implements FallbackFactory<PmsFeignClinent> {
    @Override
    public PmsFeignClinent create(Throwable throwable) {
        return new PmsFeignClinent() {

            @Override
            public PmsProduct selectById(Long id) {
                log.error("通过id查询商品异常:{}", id, throwable);
                return new PmsProduct();
            }

            @Override
            public PmsSkuStock selectSkuById(Long id) {
                return null;
            }

            @Override
            public List<PromotionProduct> getPromotionProductList(List<Long> productIdList) {
                return null;
            }

            @Override
            public void updateSkuById(PmsSkuStock skuStock) {

            }

            @Override
            public void updateGoodsById(PmsProduct goods) {

            }

            @Override
            public PmsGifts getGiftById(Long goodsId) {
                return null;
            }

            @Override
            public void updateGiftById(PmsGifts newGoods) {

            }

            @Override
            public List<PmsProduct> listGoodsByIds(List<Long> ids) {
                return null;
            }

            @Override
            public void saveProductConsult(PmsProductConsult productConsult) {

            }
        };
    }
}
