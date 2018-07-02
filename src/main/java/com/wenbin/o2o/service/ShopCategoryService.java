package com.wenbin.o2o.service;

import com.wenbin.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
