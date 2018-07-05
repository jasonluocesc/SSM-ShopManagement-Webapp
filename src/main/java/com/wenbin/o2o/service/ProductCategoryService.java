package com.wenbin.o2o.service;

import com.wenbin.o2o.dto.ProductCategoryExecution;
import com.wenbin.o2o.dto.ShopExecution;
import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;
}
