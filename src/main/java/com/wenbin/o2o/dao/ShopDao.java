package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface ShopDao {

    /*
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    // create shop
    int insertShop(Shop shop);

    // update shop info
    int updateShop(Shop shop);

    Shop queryByShopId(long shopId);
}
