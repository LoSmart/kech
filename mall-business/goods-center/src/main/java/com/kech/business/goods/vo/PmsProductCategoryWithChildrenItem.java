package com.kech.business.goods.vo;


import com.kech.common.entity.pms.PmsProductCategory;

import java.util.List;

public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
    private List<PmsProductCategory> children;

    public List<PmsProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<PmsProductCategory> children) {
        this.children = children;
    }
}
