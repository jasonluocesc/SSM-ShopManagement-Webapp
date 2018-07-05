package com.wenbin.o2o.service;

import com.wenbin.o2o.dto.ShopExecution;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.exceptions.ShopOperationException;

import java.io.InputStream;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ShopService {

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String filename) throws ShopOperationException;

    ShopExecution addShop(Shop shop, InputStream inputStream, String fileName) throws ShopOperationException;

    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
