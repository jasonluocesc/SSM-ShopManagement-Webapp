package com.wenbin.o2o.dao;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.Area;
import com.wenbin.o2o.entity.PersonInfo;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.entity.ShopCategory;
import com.wenbin.o2o.enums.ShopStateEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;



    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,0,5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表大小："+shopList.size());
        System.out.println("店铺总数："+count);
        Area area = new Area();
        area.setAreaId(2);
        shopCondition.setArea(area);
        int areaTwoCount = shopDao.queryShopCount(shopCondition);
        System.out.println("区域2店铺个数"+areaTwoCount);

    }

    @Test
    public void testQueryByShopId(){
        long shopId = 8;
        Shop shop = shopDao.queryByShopId(8);
        System.out.println(shop.getArea().getAreaName());

    }

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectNum = shopDao.insertShop(shop);
        Assert.assertEquals(1,effectNum);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);

        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectNum = shopDao.updateShop(shop);
        Assert.assertEquals(1,effectNum);
    }
}
