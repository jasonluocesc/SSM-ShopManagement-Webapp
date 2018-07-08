package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ProductImgDao {

    List<ProductImg> queryProductImgList(long productId);

    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductId(long productId);
}
