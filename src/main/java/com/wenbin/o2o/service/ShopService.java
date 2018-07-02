package com.wenbin.o2o.service;

import com.wenbin.o2o.dto.ShopExcution;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ShopService {
    ShopExcution addShop(Shop shop, InputStream inputStream,String fileName) throws ShopOperationException;
}
