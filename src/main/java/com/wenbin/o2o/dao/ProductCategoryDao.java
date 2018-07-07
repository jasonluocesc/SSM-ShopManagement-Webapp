package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategoryList(long shopId);

    int batchInsertProductCategory(List<ProductCategory> productCategoryList); //批量添加商品类目

    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
