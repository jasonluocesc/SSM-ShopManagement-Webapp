package com.wenbin.o2o.service;

import com.wenbin.o2o.dto.ImageHolder;
import com.wenbin.o2o.dto.ProductExecution;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ProductService{

    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationException;

    Product getProductById(long productId);
}
