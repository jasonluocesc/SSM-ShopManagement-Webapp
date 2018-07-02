package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.Shop;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ShopDao {

    // create shop
    int insertShop(Shop shop);

    // update shop info
    int updateShop(Shop shop);
}
