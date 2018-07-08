package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.Product;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ProductDao {
    int insertProduct(Product product);

    Product queryProductById(long productId);

    int updateProduct(Product product);

    int deleteProductImgByProductId(long productId);
}
